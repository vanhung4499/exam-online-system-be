package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Exam;
import com.hnv99.exam.model.entity.ExamQuAnswer;
import com.hnv99.exam.model.form.exam.ExamAddForm;
import com.hnv99.exam.model.form.exam.ExamUpdateForm;
import com.hnv99.exam.model.form.examquanswer.ExamQuAnswerAddForm;
import com.hnv99.exam.model.vo.exam.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface ExamService extends IService<Exam> {
    /**
     * Create an exam
     *
     * @param examAddForm Exam addition form
     * @return Result of the creation
     */
    Result<String> createExam(ExamAddForm examAddForm);

    /**
     * Update an exam
     *
     * @param examUpdateForm Exam update form
     * @param examId         Exam ID
     * @return Result of the update
     */
    Result<String> updateExam(ExamUpdateForm examUpdateForm, Integer examId);

    /**
     * Delete an exam
     *
     * @param ids IDs of exams to delete
     * @return Result of the deletion
     */
    Result<String> deleteExam(String ids);

    /**
     * Paginate and retrieve exam list for teachers
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @param title    Exam title
     * @return Paginated exam list
     */
    Result<IPage<ExamVO>> getPagingExam(Integer pageNum, Integer pageSize, String title);

    /**
     * Get list of question IDs for an exam
     *
     * @param examId Exam ID
     * @return List of question IDs
     */
    Result<ExamQuestionListVO> getQuestionList(Integer examId);

    /**
     * Get details of a single question
     *
     * @param examId     Exam ID
     * @param questionId Question ID
     * @return Details of the question
     */
    Result<ExamQuDetailVO> getQuestionSingle(Integer examId, Integer questionId);

    /**
     * Summarize questions
     *
     * @param examId Exam ID
     * @return Summarized question list
     */
    Result<List<ExamQuCollectVO>> getCollect(Integer examId);

    /**
     * Get detailed information about an exam
     *
     * @param examId Exam ID
     * @return Detailed exam information
     */
    Result<ExamDetailVO> getDetail(Integer examId);

    /**
     * Add cheating count for an exam
     *
     * @param examId Exam ID
     * @return Result of the operation
     */
    Result<Integer> addCheat(Integer examId);

    /**
     * Fill in answers for questions
     *
     * @param examQuAnswerForm Answer form
     * @return Result of the operation
     */
    Result<String> addAnswer(ExamQuAnswerAddForm examQuAnswerForm);

    /**
     * Get exams by grade
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @param title    Exam title
     * @return Paginated list of exams by grade
     */
    Result<IPage<ExamGradeListVO>> getGradeExamList(Integer pageNum, Integer pageSize, String title);

    /**
     * Submit an exam
     *
     * @param examId Exam ID
     * @return Details of the submitted exam
     */
    Result<ExamQuDetailVO> handExam(Integer examId);

    /**
     * Start an exam
     *
     * @param examId Exam ID
     * @return Result of the operation
     */
    Result<String> startExam(Integer examId);

    /**
     * Add a new exam record
     *
     * @param examQuAnswerForm Answer form
     * @param quType           Question type
     * @return Result of the operation
     */
    Result<String> insertNewAnswer(ExamQuAnswerAddForm examQuAnswerForm, Integer quType);

    /**
     * Update an exam record if it exists
     *
     * @param examQuAnswerForm Answer form
     * @param quType           Question type
     * @return Result of the operation
     */
    Result<String> updateAnswerIfExists(ExamQuAnswerAddForm examQuAnswerForm, Integer quType);

    /**
     * Convert form to entity
     *
     * @param form   Form to convert
     * @param quType Question type
     * @return Converted exam question answer
     */
    ExamQuAnswer prepareExamQuAnswer(ExamQuAnswerAddForm form, Integer quType);

    /**
     * Check if a user is taking an exam
     *
     * @param examId Exam ID
     * @return Boolean indicating if user is taking an exam
     */
    boolean isUserTakingExam(Integer examId);
}
