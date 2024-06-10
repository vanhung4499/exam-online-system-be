package com.hnv99.exam.controller;


import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.form.GradeForm;
import com.hnv99.exam.model.vo.GradeVO;
import com.hnv99.exam.service.GradeService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/grades")
public class GradeController {

    @Resource
    private GradeService gradeService;

    /**
     * Paginate to query grades
     * @param pageNum
     * @param pageSize
     * @param gradeName
     * @return
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<GradeVO>>  getGrade(@RequestParam(value = "pageNum",required = false, defaultValue = "1") Integer pageNum,
                                            @RequestParam(value = "pageSize",required = false, defaultValue = "10") Integer pageSize,
                                            @RequestParam(value = "gradeName",required = false) String gradeName) {
        return gradeService.getPaging(pageNum, pageSize, gradeName);
    }

    /**
     * Add a grade
     * @param gradeForm
     * @return
     */
    @PostMapping("/add")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> addGrade(@Validated @RequestBody GradeForm gradeForm) {
        return gradeService.addGrade(gradeForm);
    }

    /**
     * Update a grade
     * @param id
     * @param gradeForm
     * @return
     */
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> updateGrade(@PathVariable("id") @NotNull Integer id,@Validated @RequestBody GradeForm gradeForm) {
        return gradeService.updateGrade(id, gradeForm);
    }

    /**
     * Delete a grade
     * @param id
     * @return
     */
    @DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<String> deleteGrade(@PathVariable("id") @NotNull Integer id) {
        return gradeService.deleteGrade(id);
    }

    /**
     * Exit a grade
     * @param ids
     * @return
     */
    @PatchMapping("/remove/{ids}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin','role_studnet')")
    public Result<String> removeUserGrade(@PathVariable("ids") @NotBlank String ids) {
        return gradeService.removeUserGrade(ids);
    }

    /**
     * Get all grade lists
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<List<GradeVO>> getAllGrade(){
        return gradeService.getAllGrade();
    }
}
