package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.GradeConverter;
import com.hnv99.exam.mapper.ExamMapper;
import com.hnv99.exam.mapper.GradeMapper;
import com.hnv99.exam.mapper.QuestionMapper;
import com.hnv99.exam.mapper.UserMapper;
import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.form.GradeForm;
import com.hnv99.exam.model.vo.GradeVO;
import com.hnv99.exam.service.GradeService;
import com.hnv99.exam.util.ClassTokenGenerator;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;


@Service
public class GradeServiceImpl extends ServiceImpl<GradeMapper, Grade> implements GradeService {
    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private GradeConverter gradeConverter;
    @Resource
    private UserMapper userMapper;
    @Resource
    private GradeService gradeService;

    @Override
    public Result<String> addGrade(GradeForm gradeForm) {
        // Generate class token
        gradeForm.setCode(ClassTokenGenerator.generateClassToken(18));
        // Convert to entity
        Grade grade = gradeConverter.formToEntity(gradeForm);
        // Add data
        int rows = gradeMapper.insert(grade);
        if (rows == 0) {
            return Result.failed("Add failed");
        }
        return Result.success("Added successfully");
    }

    @Override
    public Result<String> updateGrade(Integer id, GradeForm gradeForm) {
        // Build update conditions
        LambdaUpdateWrapper<Grade> gradeUpdateWrapper = new LambdaUpdateWrapper<>();
        gradeUpdateWrapper
                .set(Grade::getGradeName, gradeForm.getGradeName())
                .eq(Grade::getId, id);
        // Update grade
        int rows = gradeMapper.update(gradeUpdateWrapper);
        if (rows == 0) {
            return Result.failed("Update failed");
        }
        return Result.success("Updated successfully");
    }

    @Override
    public Result<String> deleteGrade(Integer id) {
        // Delete grade
        int rows = gradeMapper.deleteById(id);
        if (rows == 0) {
            return Result.failed("Deletion failed");
        }
        return Result.success("Deleted successfully");
    }

    @Override
    public Result<IPage<GradeVO>> getPaging(Integer pageNum, Integer pageSize, String gradeName) {
        // Create pagination object
        Page<GradeVO> page = new Page<>(pageNum, pageSize);
        // Query the grades created by oneself
        Integer role = switch (SecurityUtil.getRole()) {
            case "role_teacher" -> 2;
            case "role_admin" -> 3;
            case "role_student" -> 1;
            default -> null;
        };
        if (role == null) {
            return Result.failed("Invalid role");
        }
        page = gradeMapper.selectGradePage(page, SecurityUtil.getUserId(), gradeName, role);

        return Result.success("Query successful", page);
    }

    @Override
    public Result<String> removeUserGrade(String ids) {
        // Convert string to list
        List<Integer> userIds = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .toList();
        // Remove from grade
        int rows = userMapper.removeUserGrade(userIds);
        if (rows == 0) {
            return Result.failed("Removal failed");
        }
        return Result.success("Removed successfully");
    }

    @Override
    public Result<List<GradeVO>> getAllGrade() {
        LambdaQueryWrapper<Grade> gradeLambdaQueryWrapper = new LambdaQueryWrapper<>();
        gradeLambdaQueryWrapper.eq(Grade::getUserId, SecurityUtil.getUserId())
                .eq(Grade::getIsDeleted,0);
        List<Grade> grades = gradeMapper.selectList(gradeLambdaQueryWrapper);
        List<GradeVO> gradeVOS = gradeConverter.listEntityToVo(grades);
        return Result.success("Query Successful", gradeVOS);
    }

}

