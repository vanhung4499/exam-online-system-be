package com.hnv99.exam.controller;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.vo.record.ExamRecordDetailVO;
import com.hnv99.exam.model.vo.record.ExamRecordVO;
import com.hnv99.exam.model.vo.record.ExerciseRecordDetailVO;
import com.hnv99.exam.model.vo.record.ExerciseRecordVO;
import com.hnv99.exam.service.ExerciseRecordService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/records")
public class RecordController {
    @Resource
    private ExerciseRecordService exerciseRecordService;

    /**
     * Paging query of examined papers
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @return Response result
     */
    @GetMapping("/exam/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<IPage<ExamRecordVO>> getExamRecordPage(@RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                                         @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                                                         @RequestParam(value = "examName", required = false) String examName){
        return exerciseRecordService.getExamRecordPage(pageNum,pageSize,examName);
    }

    /**
     * Query paper details
     *
     * @param examId Exam ID
     * @return Response result
     */
    @GetMapping("/exam/detail")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<List<ExamRecordDetailVO>> getExamRecordDetail(@RequestParam("examId") Integer examId) {
        return exerciseRecordService.getExamRecordDetail(examId);
    }

    /**
     * Paging query of exercises that have been taken
     *
     * @param pageNum  Page number
     * @param pageSize Number of records per page
     * @return Response result
     */
    @GetMapping("/exercise/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<IPage<ExerciseRecordVO>> getExerciseRecordPage(@RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                                                 @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                                                                 @RequestParam(value = "repoName", required = false) String repoName){
        return exerciseRecordService.getExerciseRecordPage(pageNum,pageSize,repoName);
    }

    /**
     * Query exercise details
     *
     * @param exerciseId Exercise ID
     * @return Response result
     */
    @GetMapping("/exercise/detail")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<List<ExerciseRecordDetailVO>> getExerciseRecordDetail(@RequestParam("repoId") Integer exerciseId) {
        return exerciseRecordService.getExerciseRecordDetail(exerciseId);
    }
}
