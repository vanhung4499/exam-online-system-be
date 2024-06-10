package com.hnv99.exam.controller;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.vo.stat.AllStatsVO;
import com.hnv99.exam.model.vo.stat.DailyVO;
import com.hnv99.exam.model.vo.stat.GradeExamVO;
import com.hnv99.exam.model.vo.stat.GradeStudentVO;
import com.hnv99.exam.service.StatService;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/stat")
public class StatController {

    @Resource
    private StatService statService;

    /**
     * Statistics of the number of students in each class
     * @return Response result
     */
    @GetMapping("/student")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<List<GradeStudentVO>> getStudentGradeCount() {
        return statService.getStudentGradeCount();
    }

    /**
     * Statistics of each class exam
     * @return Response result
     */
    @GetMapping("/exam")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<List<GradeExamVO>> getExamGradeCount() {
        return statService.getExamGradeCount();
    }

    /**
     * Statistics of the total number of classes, exams, and questions
     * @return Statistics result
     */
    @GetMapping("/allCounts")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<AllStatsVO> getAllCount(){
        return statService.getAllCount();
    }

    /**
     * Get daily statistics
     * @return Daily statistics result
     */
    @GetMapping("/daily")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<List<DailyVO>> getDaily(){
        return statService.getDaily();
    }

}
