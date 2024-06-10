package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.UserConverter;
import com.hnv99.exam.mapper.*;
import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.entity.User;
import com.hnv99.exam.model.form.UserForm;
import com.hnv99.exam.model.vo.UserVO;
import com.hnv99.exam.service.QuestionService;
import com.hnv99.exam.service.UserService;
import com.hnv99.exam.util.DateTimeUtil;
import com.hnv99.exam.util.SecurityUtil;
import com.hnv99.exam.util.excel.ExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private HttpServletRequest request;
    @Resource
    private UserConverter userConverter;
    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private CertificateUserMapper certificateUserMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private ExamQuAnswerMapper examQuAnswerMapper;
    @Resource
    private ExerciseRecordMapper exerciseRecordMapper;
    @Resource
    private GradeExerciseMapper gradeExerciseMapper;
    @Resource
    private ManualScoreMapper manualScoreMapper;
    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private RepoMapper repoMapper;
    @Resource
    private UserBookMapper userBookMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private NoticeGradeMapper noticeGradeMapper;
    @Resource
    private QuestionService iQuestionService;


    @Override
    public Result<String> createUser(UserForm userForm) {
        // Set default password
        userForm.setPassword(new BCryptPasswordEncoder().encode("123456"));
        // Teachers can only create students
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            userForm.setRoleId(1);
        }
        // Avoid admins creating users without specifying a role
        if (userForm.getRoleId() == null || userForm.getRoleId() == 0) {
            return Result.failed("Please select a user role");
        }
        User user = userConverter.fromToEntity(userForm);
        userMapper.insert(user);
        return Result.success("User created successfully");
    }

    @Override
    public Result<String> updatePassword(UserForm userForm) {
        // Check if new passwords match
        if (!userForm.getNewPassword().equals(userForm.getCheckedPassword())) {
            return Result.failed("Passwords do not match");
        }
        Integer userId = SecurityUtil.getUserId();
        User user = userMapper.selectById(userId);
        // Check if old password matches
        if (!new BCryptPasswordEncoder().matches(userForm.getOriginPassword(), user.getPassword())) {
            return Result.failed("Incorrect old password");
        }
        // Encrypt new password
        userForm.setPassword(new BCryptPasswordEncoder().encode(userForm.getNewPassword()));
        userForm.setId(userId);
        int updated = userMapper.updateById(userConverter.fromToEntity(userForm));
        // Clear token from Redis if password is successfully updated, prompting user to log in again
        if (updated > 0) {
            stringRedisTemplate.delete(request.getSession().getId() + "token");
            return Result.success("Password updated successfully. Please log in again");
        }
        return Result.failed("Failed to update password");
    }

    @Override
    @Transactional
    public Result<String> deleteBatchByIds(String ids) {
        List<Integer> userIds = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        // Delete user certificates
        certificateUserMapper.deleteByUserIds(userIds);
        // Delete exams created by users
        examMapper.deleteByUserIds(userIds);
        // Delete user exam answer records
        examQuAnswerMapper.deleteByUserIds(userIds);
        // Delete user exercise records
        exerciseRecordMapper.deleteByUserIds(userIds);
        // Clear class IDs from user table
        List<Integer> gradeIds = gradeMapper.selectIdsByUserIds(userIds);
        if (!gradeIds.isEmpty()) {
            userMapper.removeGradeIdByGradeIds(gradeIds);
        }
        // Delete classes created by users
        gradeMapper.deleteByUserId(userIds);
        // Delete class exercise associations created by users
        gradeExerciseMapper.deleteByUserIds(userIds);
        // Delete subjective question scores corrected by users
        manualScoreMapper.deleteByUserIds(userIds);
        // Delete notice-class associations
        // 1. Get notice IDs
        List<Integer> noticeIds = noticeMapper.selectIdsByUserIds(userIds);
        // 2. Delete notice-class associations
        if (!noticeIds.isEmpty()) {
            noticeGradeMapper.deleteByNoticeIds(noticeIds);
        }
        // Delete notices created by users
        noticeMapper.deleteByUserIds(userIds);
        // Delete question options
        // 1. Get question IDs created by users
        List<Integer> quIds = questionMapper.selectIdsByUserIds(userIds);
        // 2. Delete options based on question IDs
        if (!quIds.isEmpty()) {
            optionMapper.deleteBatchByQuIds(quIds);
        }
        // Delete questions created by users
        questionMapper.deleteByUserIds(userIds);
        // Delete question banks created by users
        repoMapper.deleteByUserIds(userIds);
        // Delete user's error book
        userBookMapper.deleteByUserIds(userIds);
        // Delete users
        userMapper.deleteBatchIds(userIds);
        return Result.success("Deletion successful");
    }

    @SneakyThrows(Exception.class)
    @Override
    @Transactional
    public Result<String> importUsers(MultipartFile file) {
        // File type check
        if (!ExcelUtils.isExcel(Objects.requireNonNull(file.getOriginalFilename()))) {
            return Result.failed("File type must be xls or xlsx");
        }
        // Read the file
        List<UserForm> list = ExcelUtils.readMultipartFile(file, UserForm.class);
        // Supplement parameters
        list.forEach(userForm -> {
            userForm.setPassword(new BCryptPasswordEncoder().encode("123456"));
            userForm.setCreateTime(DateTimeUtil.getDateTime());
            if (userForm.getRoleId() == null) {
                userForm.setRoleId(1);
            }
        });
        if (list.size() > 300) {
            return Result.failed("Maximum of 300 data entries allowed in the table");
        }
        userMapper.insertBatchUser(userConverter.listFromToEntity(list));
        return Result.success("User imported successfully");
    }


    @Override
    public Result<UserVO> info() {
        UserVO userVo = userMapper.info(SecurityUtil.getUserId());
        userVo.setPassword(null);
        return Result.success(null, userVo);
    }

    @Override
    public Result<String> joinGrade(String code) {
        // Get class information
        LambdaQueryWrapper<Grade> wrapper = new LambdaQueryWrapper<Grade>().eq(Grade::getCode, code);
        Grade grade = gradeMapper.selectOne(wrapper);
        if (Objects.isNull(grade)) {
            return Result.failed("Class code does not exist");
        }
        User user = new User();
        user.setId(SecurityUtil.getUserId());
        user.setGradeId(grade.getId());
        int updated = userMapper.updateById(user);
        if (updated > 0) {
            return Result.success("Successfully joined class: " + grade.getGradeName());
        }
        return Result.failed("Joining class failed");
    }

    @Override
    public Result<IPage<UserVO>> pagingUser(Integer pageNum, Integer pageSize, Integer gradeId, String realName) {
        IPage<UserVO> page = new Page<>(pageNum, pageSize);
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            page = userMapper.pagingUser(page, gradeId, realName, SecurityUtil.getUserId(), 1);
        } else {
            page = userMapper.pagingUser(page, gradeId, realName, SecurityUtil.getUserId(), null);
        }
        return Result.success(null, page);
    }

    @Transactional
    @Override
    public Result<String> uploadAvatar(MultipartFile file) {
        Result<String> result = iQuestionService.uploadImage(file);
        if (result.getCode() == 0) {
            return Result.failed("Failed to upload image");
        }
        String url = result.getData();
        User user = new User();
        user.setId(SecurityUtil.getUserId());
        user.setAvatar(url);
        if (userMapper.updateById(user) > 0) {
            return Result.success("Upload successful", url);
        }
        return Result.failed("Failed to upload image");
    }
}
