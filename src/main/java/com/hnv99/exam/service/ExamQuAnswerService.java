package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.ExamQuAnswer;
import com.hnv99.exam.model.vo.score.QuestionAnalyseVO;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * Exam question answer service interface
 */
public interface ExamQuAnswerService extends IService<ExamQuAnswer> {
    /**
     * Get the answer situation for a specific question in a particular exam
     * @param examId Exam ID
     * @param questionId Question ID
     * @return Result containing question analysis details
     */
    Result<QuestionAnalyseVO> questionAnalyse(Integer examId, Integer questionId);
}
