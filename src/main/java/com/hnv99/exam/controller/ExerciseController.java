package com.hnv99.exam.controller;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.ExerciseFillAnswerFrom;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.model.vo.exercise.ExerciseRepoVO;
import com.hnv99.exam.model.vo.exercise.QuestionSheetVO;
import com.hnv99.exam.service.ExerciseRecordService;
import com.hnv99.exam.service.RepoService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Nullable;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@Validated
public class ExerciseController {

    @Resource
    private ExerciseRecordService exerciseRecordService;
    @Resource
    private RepoService repoService;

    /**
     * Get a list of question IDs
     *
     * @param repoId repository ID
     * @param quType question type
     * @return response result
     */
    @GetMapping("/{repoId}")
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<List<QuestionSheetVO>> getQuestion(@PathVariable("repoId") Integer repoId,
                                                     @Min(value = 1, message = "The minimum value of question type should be 1")
                                                     @Max(value = 4, message = "The maximum value of question type should be 4")
                                                     @Nullable
                                                     @RequestParam(value = "quType", required = false) Integer quType) {
        return exerciseRecordService.getQuestionSheet(repoId, quType);
    }


    /**
     * Fill in answers and return question information
     *
     * @param exerciseFillAnswerFrom request parameters
     * @return response result
     */
    @PostMapping("/fillAnswer")
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<QuestionVO> fillAnswer(@RequestBody ExerciseFillAnswerFrom exerciseFillAnswerFrom) {
        return exerciseRecordService.fillAnswer(exerciseFillAnswerFrom);
    }

    /**
     * Paginate to get a list of repositories that can be practiced
     *
     * @param pageNum  page number
     * @param pageSize number of records per page
     * @param title    repository title
     * @return response result
     */
    @GetMapping("/getRepo")
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<IPage<ExerciseRepoVO>> getRepo(
            @RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
            @RequestParam(value = "title", required = false) String title) {
        return repoService.getRepo(pageNum, pageSize, title);
    }

    @GetMapping("/question/{id}")
    @PreAuthorize("hasAnyAuthority('role_student','role_teacher','role_admin')")
    public Result<QuestionVO> getSingle(@PathVariable("id")Integer id){
        return exerciseRecordService.getSingle(id);
    }
}
