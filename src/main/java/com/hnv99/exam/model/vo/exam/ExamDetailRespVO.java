package com.hnv99.exam.model.vo.exam;

import lombok.Data;

@Data
public class ExamDetailRespVO {
    private Integer id;
    /**
     * Unique exam ID
     */
    private Integer examId;

    /**
     * Unique question ID
     */
    private Integer questionId;
    /**
     * Score
     */
    private Integer score;
    /**
     * Sorting order
     */
    private Integer sort;
    /**
     * Type
     */
    private Integer type;
}
