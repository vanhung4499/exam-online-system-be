package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.ManualScore;
import com.hnv99.exam.model.form.answer.CorrectAnswerForm;
import com.hnv99.exam.model.vo.answer.AnswerExamVO;
import com.hnv99.exam.model.vo.answer.UncorrectedUserVO;
import com.hnv99.exam.model.vo.answer.UserAnswerDetailVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * Service interface for handling manual scoring
 * </p>
 */
public interface ManualScoreService extends IService<ManualScore> {
    /**
     * Get detailed information about a user's answers in an exam
     * @param userId User ID
     * @param examId Exam ID
     * @return Result containing a list of UserAnswerDetailVO objects
     */
    Result<List<UserAnswerDetailVO>> getDetail(Integer userId, Integer examId);

    /**
     * Grade the exam papers
     * @param correctAnswerFroms List of CorrectAnswerFrom objects containing the correct answers
     * @return Result indicating the success or failure of the operation
     */
    Result<String> correct(List<CorrectAnswerForm> correctAnswerFroms);

    /**
     * Paginated search for exams awaiting manual grading
     * @param pageNum Page number
     * @param pageSize Page size
     * @param examName Exam name
     * @return Result containing a page of AnswerExamVO objects
     */
    Result<IPage<AnswerExamVO>> examPage(Integer pageNum, Integer pageSize, String examName);

    /**
     * Paginated search for students' exams awaiting manual grading
     * @param pageNum Page number
     * @param pageSize Page size
     * @param examId Exam ID
     * @param realName Student's real name
     * @return Result containing a page of UncorrectedUserVO objects
     */
    Result<IPage<UncorrectedUserVO>> stuExamPage(Integer pageNum, Integer pageSize, Integer examId, String realName);
}
