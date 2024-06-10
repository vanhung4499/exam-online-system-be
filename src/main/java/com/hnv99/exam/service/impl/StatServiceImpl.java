package com.hnv99.exam.service.impl;

import com.hnv99.exam.mapper.ExamGradeMapper;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.mapper.*;
import com.hnv99.exam.model.entity.*;
import com.hnv99.exam.model.vo.stat.AllStatsVO;
import com.hnv99.exam.model.vo.stat.DailyVO;
import com.hnv99.exam.model.vo.stat.GradeExamVO;
import com.hnv99.exam.model.vo.stat.GradeStudentVO;
import com.hnv99.exam.service.StatService;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StatServiceImpl extends ServiceImpl<ExamGradeMapper, ExamGrade> implements StatService {

    @Resource
    private StatMapper statMapper;
    @Resource
    private GradeMapper gradeMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private UserDailyLoginDurationMapper userDailyLoginDurationMapper;

    @Override
    public Result<List<GradeStudentVO>> getStudentGradeCount() {
        List<GradeStudentVO> gradeStudentVOs;
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            gradeStudentVOs = statMapper.StudentGradeCount(2, SecurityUtil.getUserId());
        } else {
            gradeStudentVOs = statMapper.StudentGradeCount(3, SecurityUtil.getUserId());
        }
        return Result.success("Query successful", gradeStudentVOs);
    }

    @Override
    public Result<List<GradeExamVO>> getExamGradeCount() {
        List<GradeExamVO> gradeExamVOs;
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            gradeExamVOs = statMapper.ExamGradeCount(2, SecurityUtil.getUserId());
        } else {
            gradeExamVOs = statMapper.ExamGradeCount(3, SecurityUtil.getUserId());
        }
        return Result.success("Query successful", gradeExamVOs);
    }

    @Override
    public Result<AllStatsVO> getAllCount() {
        AllStatsVO allStatsVO = new AllStatsVO();
        String role = SecurityUtil.getRole();
        if ("role_admin".equals(role)) {
            allStatsVO.setClassCount(gradeMapper.selectCount(null).intValue());
            allStatsVO.setExamCount(examMapper.selectCount(null).intValue());
            allStatsVO.setQuestionCount(questionMapper.selectCount(null).intValue());
        } else if ("role_teacher".equals(role)) {
            allStatsVO.setClassCount(gradeMapper.selectCount(new LambdaQueryWrapper<Grade>().eq(Grade::getUserId,
                    SecurityUtil.getUserId())).intValue());
            allStatsVO.setExamCount(examMapper.selectCount(
                    new LambdaQueryWrapper<Exam>().eq(Exam::getUserId, SecurityUtil.getUserId())).intValue());
            allStatsVO.setQuestionCount(questionMapper.selectCount(
                    new LambdaQueryWrapper<Question>().eq(Question::getUserId, SecurityUtil.getUserId())).intValue());
        }
        return Result.success("Query successful", allStatsVO);
    }

    @Override
    public Result<List<DailyVO>> getDaily() {
        List<DailyVO> daily = userDailyLoginDurationMapper.getDaily(SecurityUtil.getUserId());
        return Result.success("Request successful", daily);
    }
}
