package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Exam;
import com.hnv99.exam.model.vo.answer.AnswerExamVO;
import com.hnv99.exam.model.vo.record.ExamRecordVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ExamMapper extends BaseMapper<Exam> {

    int deleteExams(List<Integer> examIds);

    int deleteExamGrades(List<Integer> examIds);

    int deleteExamRepos(List<Integer> examIds);

    int deleteExamQuestions(List<Integer> examIds);

    /**
     * Delete exams created by users
     *
     * @param userIds List of user IDs
     * @return Number of affected database records
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Get exams created by oneself, including exam ID, exam title, and whether grading is required
     *
     * @param page   Pagination information
     * @param userId User ID
     * @param role   User role
     * @return Paginated result
     */
    IPage<AnswerExamVO> selectMarkedList(@Param("page") IPage<AnswerExamVO> page, @Param("userId") Integer userId,String role,String examName);

    /**
     * Get exam record page
     *
     * @param examPage Paginated exam record information
     * @param userId   User ID
     * @return Paginated result
     */
    Page<ExamRecordVO> getExamRecordPage(Page<ExamRecordVO> examPage, Integer userId, String examName);
}
