package com.hnv99.exam.service.impl;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.UserConverter;
import com.hnv99.exam.mapper.RoleMapper;
import com.hnv99.exam.mapper.UserDailyLoginDurationMapper;
import com.hnv99.exam.mapper.UserMapper;
import com.hnv99.exam.model.entity.User;
import com.hnv99.exam.model.entity.UserDailyLoginDuration;
import com.hnv99.exam.model.form.auth.LoginForm;
import com.hnv99.exam.model.form.UserForm;
import com.hnv99.exam.security.SysUserDetails;
import com.hnv99.exam.service.AuthService;
import com.hnv99.exam.util.DateTimeUtil;
import com.hnv99.exam.util.JwtUtil;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.SneakyThrows;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;


@Service
public class AuthServiceImpl implements AuthService {
    private static final String HEARTBEAT_KEY_PREFIX = "user:heartbeat:";
    private static final long HEARTBEAT_INTERVAL_MILLIS = 10 * 60 * 1000; // 10 minutes

    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserConverter userConverter;
    @Resource
    private ObjectMapper objectMapper;
    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private UserDailyLoginDurationMapper userDailyLoginDurationMapper;

    /**
     * Login
     * @param request
     * @param loginForm Input parameters
     * @return Response
     */
    @SneakyThrows(JsonProcessingException.class)
    @Override
    public Result<String> login(HttpServletRequest request, LoginForm loginForm) {
        // Check if the user has passed the captcha verification
        String s = stringRedisTemplate.opsForValue().get("isVerifyCode" + request.getSession().getId());
        if (StringUtils.isBlank(s)) {
            return Result.failed("Please verify the captcha first");
        }

        // Get user information by username
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(User::getUserName, loginForm.getUsername());
        User user = userMapper.selectOne(wrapper);

        // Check if the username exists
        if (Objects.isNull(user)) {
            throw new UsernameNotFoundException("The user does not exist");
        }

        // Validate the password
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        if (!encoder.matches(loginForm.getPassword(), user.getPassword())) {
            return Result.failed("Incorrect password");
        }

        // Hide the password
        user.setPassword(null);

        // Get permissions based on user role
        List<String> permissions = roleMapper.selectCodeById(user.getRoleId());

        // Convert permissions from string to SimpleGrantedAuthority
        List<SimpleGrantedAuthority> userPermissions = permissions.stream()
                .map(permission -> new SimpleGrantedAuthority("role_" + permission)).toList();

        // Create a SysUserDetails object, which implements UserDetails interface
        SysUserDetails sysUserDetails = new SysUserDetails(user);
        // Add converted permissions to the SysUserDetails object
        sysUserDetails.setPermissions(userPermissions);

        // Convert user information to string
        String userInfo = objectMapper.writeValueAsString(user);

        // Create JWT token
        String token = jwtUtil.createJwt(userInfo, userPermissions.stream().map(String::valueOf).toList());
        // Store the token in Redis
        stringRedisTemplate.opsForValue().set("token" + request.getSession().getId(), token, 30, TimeUnit.MINUTES);

        // Wrap user information for subsequent authentication and authorization
        // Create UsernamePasswordAuthenticationToken with user info, password, and permissions list
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(sysUserDetails, user.getPassword(), userPermissions);

        // Optional, add web authentication details
        usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

        // Store user information in the context
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);

        // Clear Redis captcha verification
        stringRedisTemplate.delete("isVerifyCode" + request.getSession().getId());

        // Return success response with token
        return Result.success("Login successful", token);
    }

    @Override
    public Result<String> logout(HttpServletRequest request) {

        // Clear the session
        HttpSession session = request.getSession(false);

        if (session != null) {
            // Clear Redis
            stringRedisTemplate.delete("token" + session.getId());
            session.invalidate();
        }

        return Result.success("Logout successful");
    }


    @SneakyThrows(IOException.class)
    @Override
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        // Generate a line captcha with the specified width, height, number of characters, and number of interference lines
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(200, 100, 4, 500);

        // Store the captcha in Redis
        // Get the captcha code
        String code = captcha.getCode();
        String key = "code" + request.getSession().getId();
        stringRedisTemplate.opsForValue().set(key, code, 5, TimeUnit.MINUTES);
        // Write the image to the output stream
        response.setContentType("image/jpeg");
        ServletOutputStream os = response.getOutputStream();
        captcha.write(os);
        os.close();
    }

    @Override
    public Result<String> verifyCode(HttpServletRequest request, String code) {
        String key = "code" + request.getSession().getId();
        String rightCode = stringRedisTemplate.opsForValue().get(key);
        if (StringUtils.isBlank(rightCode)) {
            return Result.failed("The captcha has expired");
        }
        if (!rightCode.equalsIgnoreCase(code)) {
            return Result.failed("Incorrect captcha");
        }
        // After captcha verification, remove the captcha from Redis to prevent reuse
        stringRedisTemplate.delete(key);
        // After captcha verification, store a success flag in Redis to prevent users from bypassing the captcha during login or registration
        stringRedisTemplate.opsForValue().set("isVerifyCode" + request.getSession().getId(), "1", 5, TimeUnit.MINUTES);
        return Result.success("Captcha verification successful");
    }


    @Override
    public Result<String> register(HttpServletRequest request, UserForm userForm) {
        String s = stringRedisTemplate.opsForValue().get("isVerifyCode" + request.getSession().getId());
        if (StringUtils.isBlank(s)) {
            return Result.failed("Please verify the captcha first");
        }
        if (!userForm.getPassword().equals(userForm.getCheckedPassword())) {
            return Result.failed("The passwords do not match");
        }

        User user = userConverter.fromToEntity(userForm);

        user.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
        user.setRoleId(1);
        userMapper.insert(user);
        // After successful registration, remove the captcha validation flag from Redis to prevent immediate login using the same validation
        stringRedisTemplate.delete("isVerifyCode" + request.getSession().getId());
        return Result.success("Registration successful");
    }

    /**
     * User sends a heartbeat, updating the last active time.
     *
     * @return
     */
    @Override
    @SneakyThrows(value = JsonProcessingException.class)
    public Result<String> sendHeartbeat(HttpServletRequest request) {

        String key = HEARTBEAT_KEY_PREFIX + SecurityUtil.getUserId();
        String lastHeartbeatStr = stringRedisTemplate.opsForValue().getAndDelete(key);
        LocalDateTime now = LocalDateTime.now(ZoneOffset.UTC);
        stringRedisTemplate.opsForValue().set(key, now.toString());
        if (lastHeartbeatStr != null) {
            LocalDateTime lastHeartbeat = LocalDateTime.parse(lastHeartbeatStr);
            Duration durationSinceLastHeartbeat = Duration.between(lastHeartbeat, LocalDateTime.now(ZoneOffset.UTC));
            LocalDate date = DateTimeUtil.getDate();
            // Implement cumulative logic, such as updating records in the database
            LambdaQueryWrapper<UserDailyLoginDuration> userDailyLoginDurationLambdaQueryWrapper = new LambdaQueryWrapper<>();
            userDailyLoginDurationLambdaQueryWrapper.eq(UserDailyLoginDuration::getUserId, SecurityUtil.getUserId())
                    .eq(UserDailyLoginDuration::getLoginDate, date);
            List<UserDailyLoginDuration> userDailyLoginDurations =
                    userDailyLoginDurationMapper.selectList(userDailyLoginDurationLambdaQueryWrapper);
            if (userDailyLoginDurations.isEmpty()) {
                UserDailyLoginDuration userDailyLoginDuration = new UserDailyLoginDuration();
                userDailyLoginDuration.setUserId(SecurityUtil.getUserId());
                userDailyLoginDuration.setLoginDate(date);
                userDailyLoginDuration.setTotalSeconds(0);
                userDailyLoginDurationMapper.insert(userDailyLoginDuration);
            } else {
                UserDailyLoginDuration userDailyLoginDuration = new UserDailyLoginDuration();
                userDailyLoginDuration.setTotalSeconds(userDailyLoginDurations.get(0)
                        .getTotalSeconds() + (int) durationSinceLastHeartbeat.getSeconds());
                userDailyLoginDuration.setId(userDailyLoginDurations.get(0).getId());
                userDailyLoginDurationMapper.updateById(userDailyLoginDuration);
            }
        }
        ArrayList<String> permissions = new ArrayList<>();
        permissions.add(SecurityUtil.getRole());
        SysUserDetails principal = (SysUserDetails) (SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        User user = principal.getUser();
        String string = objectMapper.writeValueAsString(user);
        String jwt = jwtUtil.createJwt(string, permissions);
        stringRedisTemplate.opsForValue().set("token" + request.getSession().getId(), jwt, 30, TimeUnit.MINUTES);
        return Result.success("Request successful", jwt);
    }
}
