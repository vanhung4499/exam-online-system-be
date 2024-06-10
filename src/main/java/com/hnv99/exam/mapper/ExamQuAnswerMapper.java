package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.ExamQuAnswer;
import com.hnv99.exam.model.vo.answer.UserAnswerDetailVO;
import com.hnv99.exam.model.vo.exam.ExamQuAnswerExtVO;
import com.hnv99.exam.model.vo.score.QuestionAnalyseVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ExamQuAnswerMapper extends BaseMapper<ExamQuAnswer> {

    List<ExamQuAnswerExtVO> list(String examId, String questionId);

    /**
     * Get single question answering information
     * @param examId Exam ID
     * @param questionId Question ID
     * @return Query result
     */
    QuestionAnalyseVO questionAnalyse(Integer examId, Integer questionId);

    /**
     * Delete user exam answer records
     * @param userIds List of user IDs
     * @return Number of affected records
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Get user subjective question answer information
     * @param userId User ID
     * @param examId Exam ID
     * @return Result set
     */
    List<UserAnswerDetailVO> selectUserAnswer(Integer userId, Integer examId);
}
