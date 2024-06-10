package com.hnv99.exam.model.form.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LoginForm {

    /**
     * Username
     */
    @NotBlank(message = "Username cannot be empty")
    private String username;

    /**
     * Password
     */
    @NotBlank(message = "Password cannot be empty")
    private String password;
}
