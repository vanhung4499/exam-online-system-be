package com.hnv99.exam.model.vo.exam;

import lombok.Data;

@Data
public class ExamGradeVO {
    private Integer id;

    /**
     * Unique exam ID
     */
    private Integer examId;

    /**
     * Unique grade ID
     */
    private Integer gradeId;
}
