package com.hnv99.exam.controller;


import com.hnv99.exam.common.group.QuestionGroup;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.question.QuestionForm;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.service.QuestionService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {

    @Resource
    private QuestionService iQuestionService;

    /**
     * Add a single question
     * @param questionForm Request body
     * @return Response
     */
    @PostMapping("/single")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> addSingleQuestion(@Validated(QuestionGroup.QuestionAddGroup.class) @RequestBody QuestionForm questionForm) {
        return iQuestionService.addSingleQuestion(questionForm);
    }

    /**
     * Delete multiple questions
     * @param ids Question ids
     * @return Response
     */
    @DeleteMapping("/batch/{ids}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteBatchQuestion(@PathVariable("ids") String ids) {
        return iQuestionService.deleteBatchByIds(ids);
    }

    /**
     * Paginate to query questions
     * @param pageNum  Page number
     * @param pageSize Records per page
     * @param content  Question content
     * @param repoId   Repository id
     * @param type     Question type
     * @return Response
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<QuestionVO>> pagingQuestion(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                    @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                    @RequestParam(value = "content", required = false) String content,
                                                    @RequestParam(value = "repoId", required = false) Integer repoId,
                                                    @RequestParam(value = "type", required = false) Integer type) {
        return iQuestionService.pagingQuestion(pageNum, pageSize, content, type, repoId);
    }

    /**
     * Get details of a single question by question id
     * @param id Question id
     * @return Response
     */
    @GetMapping("/single/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<QuestionVO> querySingle(@PathVariable("id") Integer id) {
        return iQuestionService.querySingle(id);
    }

    /**
     * Update a question
     * @param id           Question Id
     * @param questionFrom Request body
     * @return Response
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateQuestion(@PathVariable("id") Integer id, @RequestBody QuestionForm questionFrom) {
        questionFrom.setId(id);
        return iQuestionService.updateQuestion(questionFrom);
    }

    /**
     * Import questions in bulk
     * @param id   Repository ID
     * @param file Excel file
     * @return Response
     */
    @PostMapping("/import/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> importQuestion(@PathVariable("id") Integer id, @RequestParam("file") MultipartFile file) {
        return iQuestionService.importQuestion(id, file);
    }

    /**
     * Upload image
     * @param file File
     * @return Uploaded address
     */
    @PostMapping("/uploadImage")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> uploadImage(@RequestPart("file") MultipartFile file) {
        return iQuestionService.uploadImage(file);
    }
}
