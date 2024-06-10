package com.hnv99.exam.controller;

import com.hnv99.exam.common.group.AnswerGroup;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.answer.CorrectAnswerForm;
import com.hnv99.exam.model.vo.answer.AnswerExamVO;
import com.hnv99.exam.model.vo.answer.UncorrectedUserVO;
import com.hnv99.exam.model.vo.answer.UserAnswerDetailVO;
import com.hnv99.exam.service.ManualScoreService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/answers")
public class AnswerController {

    @Resource
    private ManualScoreService manualScoreService;

    /**
     * Get detailed information about answers
     */
    @GetMapping("/detail")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<List<UserAnswerDetailVO>> getDetail(@RequestParam Integer userId,
                                                      @RequestParam Integer examId) {
        return manualScoreService.getDetail(userId, examId);
    }

    /**
     * Correct exam papers
     */
    @PutMapping("/correct")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> Correct(@RequestBody @Validated(AnswerGroup.CorrectGroup.class) List<CorrectAnswerForm> correctAnswerFroms) {
        return manualScoreService.correct(correctAnswerFroms);
    }

    /**
     * Paginate to find exams awaiting grading
     */
    @GetMapping("/exam/page")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<AnswerExamVO>> examPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                @RequestParam(value = "examName", required = false) String examName) {
        return manualScoreService.examPage(pageNum, pageSize, examName);
    }

    /**
     * Query users awaiting grading
     */
    @GetMapping("/exam/stu")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<UncorrectedUserVO>> stuExamPage(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                        @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                        @RequestParam(value = "examId") Integer examId,
                                                        @RequestParam(value = "realName", required = false) String realName) {
        return manualScoreService.stuExamPage(pageNum, pageSize, examId,realName);
    }
}
