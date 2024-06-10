package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.ExamGrade;
import com.hnv99.exam.model.vo.exam.ExamGradeListVO;
import com.hnv99.exam.model.vo.score.GradeScoreVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
public interface ExamGradeMapper extends BaseMapper<ExamGrade> {

    Integer addExamGrade(Integer id, List<Integer> gradeIds);

    Integer delExamGrade(Integer id);

    /**
     * Get paginated exam grade user information
     * @param page Paginated information
     * @param examTitle Exam title
     * @param userId User ID
     * @param gradeId Grade ID
     * @param roleId Role ID
     * @return Paginated result
     */
    IPage<GradeScoreVO> getExamGrade(IPage<GradeScoreVO> page, String examTitle, Integer userId, Integer gradeId, Integer roleId);

    /**
     * Get the number of people who need to participate in the exam based on the start ID
     * @param id Exam ID
     * @return Number of people
     */
    Integer selectClassSize(Integer id);

    /**
     * Select class exams based on the starting ID
     * @param examPage Paginated exam information
     * @param userId User ID
     * @param title Exam title
     * @return Paginated result
     */
    IPage<ExamGradeListVO> selectClassExam(IPage<ExamGradeListVO> examPage, Integer userId, String title);

    /**
     * Select admin class exams based on the title
     * @param examPage Paginated exam information
     * @param title Exam title
     * @return Paginated result
     */
    IPage<ExamGradeListVO> selectAdminClassExam(IPage<ExamGradeListVO> examPage, String title);
}
