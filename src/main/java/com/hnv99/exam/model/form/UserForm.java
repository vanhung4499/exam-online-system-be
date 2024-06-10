package com.hnv99.exam.model.form;

import com.hnv99.exam.common.group.UserGroup;
import com.hnv99.exam.util.excel.ExcelImport;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserForm {

    private Integer id;

    private LocalDateTime createTime;

    // Validator annotation
    @NotBlank(groups = {UserGroup.CreateUserGroup.class, UserGroup.RegisterGroup.class}, message = "Username cannot be empty")
    // EasyExcel annotation, mapping relationship
    @ExcelImport(value = "Username*", unique = true, required = true)
    private String userName;

    @NotBlank(groups = UserGroup.RegisterGroup.class, message = "Password cannot be empty")
    private String password;

    @NotBlank(groups = {UserGroup.CreateUserGroup.class, UserGroup.RegisterGroup.class}, message = "Real name cannot be empty")
    @ExcelImport(value = "Real name*")
    private String realName;

    @ExcelImport(value = "Role")
    private Integer roleId;

    @NotBlank(groups = {UserGroup.UpdatePasswordGroup.class}, message = "Original password cannot be empty")
    private String originPassword;

    @NotBlank(groups = {UserGroup.UpdatePasswordGroup.class}, message = "New password cannot be empty")
    private String newPassword;

    @NotBlank(groups = {UserGroup.UpdatePasswordGroup.class, UserGroup.RegisterGroup.class}, message = "Validated password cannot be empty")
    private String checkedPassword;
}
