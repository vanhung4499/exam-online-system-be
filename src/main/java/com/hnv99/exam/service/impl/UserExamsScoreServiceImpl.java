package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.mapper.ExamGradeMapper;
import com.hnv99.exam.mapper.ExamMapper;
import com.hnv99.exam.mapper.UserExamsScoreMapper;
import com.hnv99.exam.model.entity.Exam;
import com.hnv99.exam.model.entity.UserExamsScore;
import com.hnv99.exam.model.vo.score.ExportScoreVO;
import com.hnv99.exam.model.vo.score.GradeScoreVO;
import com.hnv99.exam.model.vo.score.UserScoreVO;
import com.hnv99.exam.service.UserExamsScoreService;
import com.hnv99.exam.util.SecurityUtil;
import com.hnv99.exam.util.excel.ExcelUtils;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserExamsScoreServiceImpl extends ServiceImpl<UserExamsScoreMapper, UserExamsScore> implements UserExamsScoreService {
    @Resource
    private UserExamsScoreMapper userExamsScoreMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private ExamGradeMapper examGradeMapper;

    @Override
    public Result<IPage<UserScoreVO>> pagingScore(Integer pageNum, Integer pageSize, Integer gradeId, Integer examId, String realName) {
        IPage<UserScoreVO> page = new Page<>(pageNum, pageSize);
        page = userExamsScoreMapper.pagingScore(page, gradeId, examId, realName);
        return Result.success(null, page);
    }

    @Override
    public void exportScores(HttpServletResponse response, Integer examId, Integer gradeId) {
        // Get score information
        List<ExportScoreVO> scores = userExamsScoreMapper.selectScores(examId, gradeId);
        final int[] sort = {0};
        scores.forEach(exportScoreVO -> exportScoreVO.setRanking(++sort[0]));

        // Get exam name
        LambdaQueryWrapper<Exam> wrapper = new LambdaQueryWrapper<Exam>().eq(Exam::getId, examId).select(Exam::getTitle);
        Exam exam = examMapper.selectOne(wrapper);
        // Generate table and respond
        ExcelUtils.export(response, exam.getTitle(), scores, ExportScoreVO.class);
    }

    @Override
    public Result<IPage<GradeScoreVO>> getExamScoreInfo(Integer pageNum, Integer pageSize, String examTitle, Integer gradeId) {
        IPage<GradeScoreVO> page = new Page<>(pageNum, pageSize);
        if ("role_teacher".equals(SecurityUtil.getRole())) {
            page = userExamsScoreMapper.scoreStatistics(page, gradeId, examTitle, SecurityUtil.getUserId(), 2);
        } else {
            page = userExamsScoreMapper.scoreStatistics(page, gradeId, examTitle, SecurityUtil.getUserId(), 3);
        }

        return Result.success("Query successful", page);
    }
}
