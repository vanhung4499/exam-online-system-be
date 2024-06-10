package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.vo.stat.GradeExamVO;
import com.hnv99.exam.model.vo.stat.GradeStudentVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface StatMapper extends BaseMapper<Grade> {

    /**
     * Count the number of students in each class.
     * @return Total number of students
     */
    List<GradeStudentVO> StudentGradeCount(@Param("roleId") Integer roleId, Integer id);

    /**
     * Count the number of exams in each class.
     * @return Total number of exams
     */
    List<GradeExamVO> ExamGradeCount(@Param("roleId") Integer roleId, Integer id);
}
