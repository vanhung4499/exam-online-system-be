package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.form.GradeForm;
import com.hnv99.exam.model.vo.GradeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

public interface GradeService extends IService<Grade> {

    /**
     * Add a class
     *
     * @param gradeForm Grade form
     * @return Result of the operation
     */
    Result<String> addGrade(GradeForm gradeForm);

    /**
     * Update a class
     *
     * @param id        Class ID
     * @param gradeForm Grade form
     * @return Result of the operation
     */
    Result<String> updateGrade(Integer id, GradeForm gradeForm);

    /**
     * Delete a class
     *
     * @param id Class ID
     * @return Result of the operation
     */
    Result<String> deleteGrade(Integer id);

    /**
     * Paginate through classes
     *
     * @param pageNum   Page number
     * @param pageSize  Page size
     * @param gradeName Grade name
     * @return Result of the operation
     */
    Result<IPage<GradeVO>> getPaging(Integer pageNum, Integer pageSize, String gradeName);

    /**
     * Remove users from a class
     *
     * @param ids Class IDs
     * @return Result of the operation
     */
    Result<String> removeUserGrade(String ids);

    /**
     * Get all class lists
     *
     * @return Result of the operation
     */
    Result<List<GradeVO>> getAllGrade();
}

