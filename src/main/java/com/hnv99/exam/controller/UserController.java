package com.hnv99.exam.controller;

import com.hnv99.exam.common.group.UserGroup;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.UserForm;
import com.hnv99.exam.model.vo.UserVO;
import com.hnv99.exam.service.UserService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * User management
 *
 * @author [Author Name]
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Resource
    private UserService userService;


    /**
     * Get user login information
     *
     * @return Response result
     */
    @GetMapping("/info")
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<UserVO> info() {
        return userService.info();
    }


    /**
     * Create user. Teachers can only create students, administrators can create teachers and students
     *
     * @param userForm Request parameters, username, real name [and role id]
     * @return Response result
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> createUser(@Validated(UserGroup.CreateUserGroup.class) @RequestBody UserForm userForm) {
        return userService.createUser(userForm);
    }

    /**
     * User password modification
     *
     * @param userForm Input parameter
     * @return Response result
     */
    @PutMapping
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<String> updatePassword(@Validated(UserGroup.UpdatePasswordGroup.class) @RequestBody UserForm userForm) {
        return userService.updatePassword(userForm);
    }

    /**
     * Batch delete users
     *
     * @param ids String ids
     * @return Response result
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteBatchByIds(@PathVariable("ids") String ids) {
        return userService.deleteBatchByIds(ids);
    }

    /**
     * Excel import user data
     *
     * @param file File
     * @return Response result
     */
    @PostMapping("/import")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> importUsers(@RequestParam("file") MultipartFile file) {
        return userService.importUsers(file);
    }


    /**
     * User join class, only students can join class
     *
     * @param code Class code
     * @return Response
     */
    @PutMapping("/grade/join")
    @PreAuthorize("hasAnyAuthority('role_student')")
    public Result<String> joinGrade(@RequestParam("code") String code) {
        return userService.joinGrade(code);
    }

    /**
     * Get user information paging
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @param gradeId  Class ID
     * @param realName Real name
     * @return Response result
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<UserVO>> pagingUser(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "gradeId", required = false) Integer gradeId,
                                            @RequestParam(value = "realName", required = false) String realName) {
        return userService.pagingUser(pageNum, pageSize, gradeId, realName);
    }

    /**
     * User upload avatar
     *
     * @param file File
     * @return Response result
     */
    @PutMapping("/uploadAvatar")
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<String> uploadAvatar(@RequestPart("file") MultipartFile file) {
        return userService.uploadAvatar(file);
    }
}
