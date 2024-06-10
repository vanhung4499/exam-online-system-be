package com.hnv99.exam.controller;

import com.hnv99.exam.common.group.UserGroup;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.auth.LoginForm;
import com.hnv99.exam.model.form.UserForm;
import com.hnv99.exam.service.AuthService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auths")
public class AuthController {

    @Resource
    private AuthService authService;

    /**
     * User login
     * @param request request object for getting sessionId
     * @param loginForm user information
     * @return token
     */
    @PostMapping("/login")
    public Result<String> login(HttpServletRequest request,
                                @Validated @RequestBody LoginForm loginForm) {
        return authService.login(request, loginForm);
    }

    /**
     * User logout
     * @param request request object for clearing session content
     * @return response result
     */
    @DeleteMapping("/logout")
    public Result<String> logout(HttpServletRequest request) {
        return authService.logout(request);
    }

    /**
     * User registration, only students can register
     * @param request request object for getting sessionId
     * @param userForm user information
     * @return response result
     */
    @PostMapping("/register")
    public Result<String> register(HttpServletRequest request,
                                   @RequestBody @Validated(UserGroup.RegisterGroup.class) UserForm userForm) {
        return authService.register(request, userForm);
    }

    /**
     * Get image captcha
     * @param request request object for getting sessionId
     * @param response response object for image response
     */
    @GetMapping("/captcha")
    public void getCaptcha(HttpServletRequest request, HttpServletResponse response) {
        authService.getCaptcha(request, response);
    }

    /**
     * Verify captcha
     * @param request request object for getting sessionId
     * @param code user input code
     * @return response result
     */
    @PostMapping("/verifyCode/{code}")
    public Result<String> verifyCode(HttpServletRequest request, @PathVariable("code") String code) {
        return authService.verifyCode(request, code);
    }

    /**
     * Track presence
     * @param request request object
     * @return response result
     */
    @PostMapping("/track-presence")
    public Result<String> trackPresence(HttpServletRequest request) {
        return authService.sendHeartbeat(request);
    }
}
