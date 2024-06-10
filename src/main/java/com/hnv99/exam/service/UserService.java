package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.User;
import com.hnv99.exam.model.form.UserForm;
import com.hnv99.exam.model.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

public interface UserService extends IService<User> {

    /**
     * Create a user. Teachers can only create students, while administrators can create both teachers and students.
     *
     * @param userForm Request parameters, including username, real name [and role ID]
     * @return Response result
     */
    Result<String> createUser(UserForm userForm);

    /**
     * Change password
     * @param userForm Input parameters
     * @return Response result
     */
    Result<String> updatePassword(UserForm userForm);

    /**
     * Batch delete users
     * @param ids User IDs
     * @return Response result
     */
    Result<String> deleteBatchByIds(String ids);

    /**
     * Import user information from an Excel file
     * @param file File
     * @return Response result
     */
    Result<String> importUsers(MultipartFile file);

    /**
     * Get user information. The user ID is obtained from the session
     * @return Response result
     */
    Result<UserVO> info();

    /**
     * User joins a grade, only students can join grades
     * @param code Grade code
     * @return Response result
     */
    Result<String> joinGrade(String code);

    /**
     * Get user information with pagination
     * @param pageNum Page number
     * @param pageSize Number of records per page
     * @param gradeId Grade ID
     * @param realName Real name
     * @return Query result
     */
    Result<IPage<UserVO>> pagingUser(Integer pageNum, Integer pageSize, Integer gradeId, String realName);

    /**
     * User uploads an avatar
     * @param file File
     * @return Result
     */
    Result<String> uploadAvatar(MultipartFile file);
}
