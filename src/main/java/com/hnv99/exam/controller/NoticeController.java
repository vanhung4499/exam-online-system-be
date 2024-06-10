package com.hnv99.exam.controller;


import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.NoticeForm;
import com.hnv99.exam.model.vo.NoticeVO;
import com.hnv99.exam.service.NoticeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/notices")
public class NoticeController {

    @Resource
    private NoticeService noticeService;

    /**
     * Add a notice
     * @param noticeForm
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> addNotice(@Validated @RequestBody NoticeForm noticeForm) {
        return noticeService.addNotice(noticeForm);
    }

    /**
     * Delete a notice
     * @param ids
     * @return
     */
    @DeleteMapping("/{ids}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteNotice(@PathVariable("ids") @NotBlank String ids) {
        return noticeService.deleteNotice(ids);
    }

    /**
     * Update a notice
     * @param id
     * @param noticeForm
     * @return
     */
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateNotice(@PathVariable("id") @NotBlank String id, @Validated @RequestBody NoticeForm noticeForm) {
        return noticeService.updateNotice(id, noticeForm);
    }

    /**
     * Teacher paginate to find notices
     * @param pageNum
     * @param pageSize
     * @param title
     * @return
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<NoticeVO>> getNotice(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                             @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                             @RequestParam(value = "title", required = false) String title) {
        return noticeService.getNotice(pageNum, pageSize, title);
    }

    /**
     * Get the latest notice
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/new")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_student')")
    public Result<IPage<NoticeVO>> getNewNotice(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize) {
        return noticeService.getNewNotice(pageNum, pageSize);
    }
}
