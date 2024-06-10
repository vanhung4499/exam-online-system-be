package com.hnv99.exam.filter;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.User;
import com.hnv99.exam.security.SysUserDetails;
import com.hnv99.exam.util.JwtUtil;
import com.hnv99.exam.util.ResponseUtil;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.Resource;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Slf4j
@Component
public class VerifyTokenFilter extends OncePerRequestFilter {

    @Resource
    private JwtUtil jwtUtil;
    @Resource
    private ResponseUtil responseUtil;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        // Allow login, registration, verification code check, and captcha
        String uri = request.getRequestURI();
        if (uri.contains("login") || uri.contains("verifyCode")
                || uri.contains("captcha") || uri.contains("register")) {
            doFilter(request, response, filterChain);
            return;
        }
        // Get JWT token
        String authorization = request.getHeader("Authorization");
        // Check if it is empty
        if (StringUtils.isBlank(authorization)) {
            responseUtil.response(response, Result.failed("Authorization is empty, please log in first"));
            return;
        }
        // Check if JWT is expired
        boolean verify = jwtUtil.verifyToken(authorization);
        if (!verify) {
            responseUtil.response(response, Result.failed("Token has expired, please log in again"));
            return;
        }
        // Verify if the token exists in Redis, the key uses sessionId
        if (Boolean.FALSE.equals(stringRedisTemplate.hasKey("token" + request.getSession().getId()))) {
            responseUtil.response(response, Result.failed("Invalid token, please log in again"));
            return;
        }
        // Auto-renewal
        // stringRedisTemplate.expire("token" + request.getSession().getId(), 2, TimeUnit.HOURS);
        // Get user information and permissions from JWT
        String userInfo = jwtUtil.getUser(authorization);
        List<String> authList = jwtUtil.getAuthList(authorization);
        // Deserialize JWT token to get user information
        User sysUser = objectMapper.readValue(userInfo, User.class);
        // Transform permissions
        List<SimpleGrantedAuthority> permissions = authList.stream().map(SimpleGrantedAuthority::new).toList();
        // Create logged-in user
        SysUserDetails securityUser = new SysUserDetails(sysUser);
        securityUser.setPermissions(permissions);
        // Create a token for permission authorization. Parameters: user, password, permissions (no password because already logged in)
        UsernamePasswordAuthenticationToken token =
                new UsernamePasswordAuthenticationToken(securityUser, null, permissions);
        // Set the authorization token through the security context
        SecurityContextHolder.getContext().setAuthentication(token);
        // Allow the request to proceed
        doFilter(request, response, filterChain);
    }
}
