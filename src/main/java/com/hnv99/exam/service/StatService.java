package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.ExamGrade;
import com.hnv99.exam.model.vo.stat.AllStatsVO;
import com.hnv99.exam.model.vo.stat.DailyVO;
import com.hnv99.exam.model.vo.stat.GradeExamVO;
import com.hnv99.exam.model.vo.stat.GradeStudentVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface StatService extends IService<ExamGrade> {

    /**
     * Statistics of the number of students in each class
     *
     * @return Response result
     */
    Result<List<GradeStudentVO>> getStudentGradeCount();

    /**
     * Statistics of the number of exams in each class
     *
     * @return Response result
     */
    Result<List<GradeExamVO>> getExamGradeCount();

    /**
     * Statistics of the total number of classes, exams, and questions
     *
     * @return Response result
     */
    Result<AllStatsVO> getAllCount();

    /**
     * Daily statistics
     *
     * @return Response result
     */
    Result<List<DailyVO>> getDaily();
}
