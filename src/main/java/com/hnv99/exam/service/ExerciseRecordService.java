package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.ExerciseRecord;
import com.hnv99.exam.model.form.ExerciseFillAnswerFrom;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.model.vo.exercise.AnswerInfoVO;
import com.hnv99.exam.model.vo.exercise.QuestionSheetVO;
import com.hnv99.exam.model.vo.record.ExamRecordDetailVO;
import com.hnv99.exam.model.vo.record.ExamRecordVO;
import com.hnv99.exam.model.vo.record.ExerciseRecordDetailVO;
import com.hnv99.exam.model.vo.record.ExerciseRecordVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * Service interface for handling exercise records
 * </p>
 */
public interface ExerciseRecordService extends IService<ExerciseRecord> {

    /**
     * Get the list of question sheets for exercises
     * @param repoId Repository ID
     * @param quType Question type
     * @return Result containing a list of QuestionSheetVO objects
     */
    Result<List<QuestionSheetVO>> getQuestionSheet(Integer repoId, Integer quType);

    /**
     * Get a page of exam records
     * @param pageNum Page number
     * @param pageSize Page size
     * @param examName Exam name
     * @return Result containing a page of ExamRecordVO objects
     */
    Result<IPage<ExamRecordVO>> getExamRecordPage(Integer pageNum, Integer pageSize, String examName);

    /**
     * Get the details of an exam
     * @param examId Exam ID
     * @return Result containing a list of ExamRecordDetailVO objects
     */
    Result<List<ExamRecordDetailVO>> getExamRecordDetail(Integer examId);

    /**
     * Get a page of exercise records
     * @param pageNum Page number
     * @param pageSize Page size
     * @param repoName Repository name
     * @return Result containing a page of ExerciseRecordVO objects
     */
    Result<IPage<ExerciseRecordVO>> getExerciseRecordPage(Integer pageNum, Integer pageSize, String repoName);

    /**
     * Get the details of an exercise
     * @param exerciseId Exercise ID
     * @return Result containing a list of ExerciseRecordDetailVO objects
     */
    Result<List<ExerciseRecordDetailVO>> getExerciseRecordDetail(Integer exerciseId);

    /**
     * Fill in the answer for an exercise and return the question information
     * @param exerciseFillAnswerFrom Request parameters
     * @return Result containing a QuestionVO object
     */
    Result<QuestionVO> fillAnswer(ExerciseFillAnswerFrom exerciseFillAnswerFrom);

    /**
     * Get a single question without options
     * @param id Question ID
     * @return Result containing a QuestionVO object
     */
    Result<QuestionVO> getSingle(Integer id);

    /**
     * Get answer information
     * @param repoId Repository ID
     * @param quId Question ID
     * @return Result containing an AnswerInfoVO object
     */
    Result<AnswerInfoVO> getAnswerInfo(Integer repoId, Integer quId);
}
