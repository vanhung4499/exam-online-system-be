package com.hnv99.exam.controller;


import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.userbook.ReUserBookForm;
import com.hnv99.exam.model.vo.userbook.*;
import com.hnv99.exam.service.UserBookService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userbooks")
public class UserBookController {

    @Resource
    private UserBookService userBookService;

    /**
     * Paging query of error question exams
     * @param pageNum   Page number
     * @param pageSize  Number of records per page
     * @param examName  Exam name
     * @return Response result
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<IPage<UserPageBookVO>> getPage(@RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                                 @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                                                 @RequestParam(value = "examName",required = false) String examName){

        return userBookService.getPage(pageNum,pageSize,examName);
    }

    /**
     * Query the list of question IDs in the error book
     * @param examId Exam ID
     * @return Response result
     */
    @GetMapping("/question/list/{examId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<List<ReUserExamBookVO>> getReUserExamBook(@PathVariable("examId") Integer examId){
        return userBookService.getReUserExamBook(examId);
    }

    /**
     * Query single question
     * @param quId Question ID
     * @return Response result
     */
    @GetMapping("/question/single/{quId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<BookOneQuVO> getBookOne(@PathVariable("quId") Integer quId){
        return userBookService.getBookOne(quId);
    }

    /**
     * Fill in the answer
     * @param reUserBookForm Request body
     * @return Response result
     */
    @PostMapping("/full-book")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<AddBookAnswerVO> addBookAnswer(@RequestBody ReUserBookForm reUserBookForm){
        return userBookService.addBookAnswer(reUserBookForm);
    }
}
