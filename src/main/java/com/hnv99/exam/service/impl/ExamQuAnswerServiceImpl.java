package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.mapper.ExamQuAnswerMapper;
import com.hnv99.exam.model.entity.ExamQuAnswer;
import com.hnv99.exam.model.vo.score.QuestionAnalyseVO;
import com.hnv99.exam.service.ExamQuAnswerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;

@Service
public class ExamQuAnswerServiceImpl extends ServiceImpl<ExamQuAnswerMapper, ExamQuAnswer> implements ExamQuAnswerService {

    @Resource
    private ExamQuAnswerMapper examQuAnswerMapper;

    @Override
    public Result<QuestionAnalyseVO> questionAnalyse(Integer examId, Integer questionId) {
        QuestionAnalyseVO questionAnalyseVO = examQuAnswerMapper.questionAnalyse(examId, questionId);
        // Calculate accuracy (round to two decimal places)
        DecimalFormat format = new DecimalFormat("#.00");
        String strAccuracy = format.format((double) questionAnalyseVO.getRightCount() / questionAnalyseVO.getTotalCount());
        questionAnalyseVO.setAccuracy(Double.parseDouble(strAccuracy));
        return Result.success(null, questionAnalyseVO);
    }

}
