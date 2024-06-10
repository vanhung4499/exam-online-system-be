package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.auth.LoginForm;
import com.hnv99.exam.model.form.UserForm;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface AuthService {

    /**
     * Login
     * @param request HttpServletRequest object
     * @param loginForm LoginForm object containing login credentials
     * @return Result<String> containing response status
     */
    Result<String> login(HttpServletRequest request, LoginForm loginForm);

    /**
     * User logout
     * @param request HttpServletRequest object, used to clear session content
     * @return Result<String> containing response status
     */
    Result<String> logout(HttpServletRequest request);

    /**
     * Get image captcha
     * @param request  HttpServletRequest object, used to get sessionId
     * @param response HttpServletResponse object, used to respond with the image
     */
    void getCaptcha(HttpServletRequest request, HttpServletResponse response);

    /**
     * Verify captcha code
     * @param request HttpServletRequest object, used to get sessionId
     * @param code    Captcha code entered by the user
     * @return Result<String> containing response status
     */
    Result<String> verifyCode(HttpServletRequest request, String code);

    /**
     * User registration, only for students
     * @param request  HttpServletRequest object, used to get sessionId
     * @param userForm UserForm object containing user information
     * @return Result<String> containing response status
     */
    Result<String> register(HttpServletRequest request, UserForm userForm);

    /**
     * Send heartbeat
     * @param request HttpServletRequest object, used to get sessionId
     * @return Result<String> containing response status
     */
    Result<String> sendHeartbeat(HttpServletRequest request);
}
