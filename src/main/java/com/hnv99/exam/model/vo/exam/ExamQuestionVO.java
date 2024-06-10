package com.hnv99.exam.model.vo.exam;

import lombok.Data;


@Data
public class ExamQuestionVO {
    private Integer id;

    /**
     * Exam ID (unique)
     */
    private Integer examId;

    /**
     * Question ID (unique)
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

    /**
     * Check out
     */
    private Boolean checkout;
}
