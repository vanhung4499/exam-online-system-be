package com.hnv99.exam.controller;


import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.exam.ExamAddForm;
import com.hnv99.exam.model.form.exam.ExamUpdateForm;
import com.hnv99.exam.model.form.examquanswer.ExamQuAnswerAddForm;
import com.hnv99.exam.model.vo.exam.*;
import com.hnv99.exam.service.ExamService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exams")
public class ExamController {

    @Resource
    private ExamService examService;

    /**
     * Create an exam
     * @param examAddForm form for adding exams
     * @return response result
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> createExam(@Validated @RequestBody ExamAddForm examAddForm) {
        return examService.createExam(examAddForm);
    }

    /**
     * Start an exam
     * @param examId exam ID
     * @return response result
     */
    @GetMapping("/start")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<String> startExam(@RequestParam("examId") @NotNull Integer examId) {
        return examService.startExam(examId);
    }

    /**
     * Update an exam
     * @param examUpdateForm form for updating exams
     * @param id exam ID
     * @return response result
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateExam(@Validated @RequestBody ExamUpdateForm examUpdateForm, @PathVariable("id") @NotNull Integer id) {
        return examService.updateExam(examUpdateForm,id);
    }

    /**
     * Delete an exam
     * @param ids exam IDs
     * @return response result
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteExam(@PathVariable("ids") @Pattern(regexp = "^\\d+(,\\d+)*$|^\\d+$") String ids) {
        return examService.deleteExam(ids);
    }

    /**
     * Paginate exam list for teachers
     * @param pageNum page number
     * @param pageSize number of records per page
     * @param title exam title
     * @return response result
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<ExamVO>> getPagingExam(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                               @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                               @RequestParam(value = "title", required = false) String title) {
        return examService.getPagingExam(pageNum, pageSize, title);
    }

    /**
     * Get a list of question IDs for an exam
     * @param examId exam ID
     * @return response result
     */
    @GetMapping("/question/list/{examId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<ExamQuestionListVO> getQuestionList(@PathVariable("examId") @NotBlank Integer examId) {
        return examService.getQuestionList(examId);
    }

    /**
     * Get single question information
     * @param examId exam ID
     * @param questionId question ID
     * @return response result
     */
    @GetMapping("/question/single")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<ExamQuDetailVO> getQuestionSingle(@RequestParam("examId") Integer examId,
                                                    @RequestParam("questionId") Integer questionId) {
        return examService.getQuestionSingle(examId, questionId);
    }

    /**
     * Collect questions
     * @param examId exam ID
     * @return response result
     */
    @GetMapping("/collect/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<List<ExamQuCollectVO>> getCollect(@PathVariable("id") @NotNull Integer examId) {
        return examService.getCollect(examId);
    }

    /**
     * Get exam details
     * @param examId exam ID
     * @return response result
     */
    @GetMapping("/detail")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<ExamDetailVO> getDetail(@RequestParam("examId") @NotBlank Integer examId) {
        return examService.getDetail(examId);
    }

    /**
     * Get exams by grade
     * @param pageNum page number
     * @param pageSize number of records per page
     * @param title exam title
     * @return response result
     */
    @GetMapping("/grade")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<IPage<ExamGradeListVO>> getGradeExamList(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                           @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                           @RequestParam(value = "title", required = false) String title) {
        return examService.getGradeExamList(pageNum,pageSize,title);
    }

    /**
     * Add cheating count for an exam
     * @param examId exam ID
     * @return response result
     */
    @PutMapping("/cheat/{examId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<Integer> addCheat(@PathVariable("examId") @NotNull Integer examId) {
        return examService.addCheat(examId);
    }

    /**
     * Fill in answers
     * @param examQuAnswerForm form for adding answers
     * @return response result
     */
    @PostMapping("/full-answer")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<String> addAnswer(@Validated @RequestBody ExamQuAnswerAddForm examQuAnswerForm) {
        return examService.addAnswer(examQuAnswerForm);
    }

    /**
     * Hand in the exam
     * @param examId exam ID
     * @return response result
     */
    @GetMapping(value = "/hand-exam/{examId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<ExamQuDetailVO> handleExam(@PathVariable("examId") @NotNull Integer examId) {
        return examService.handExam(examId);
    }
}