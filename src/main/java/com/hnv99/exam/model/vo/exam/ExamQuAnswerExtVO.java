package com.hnv99.exam.model.vo.exam;

import lombok.Data;

@Data
public class ExamQuAnswerExtVO {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private Integer gradeId;

    /**
     * Image of the question
     */
    private String image;

    /**
     * Answer content
     */
    private String content;

    /**
     * Sequence
     */
    private Integer sort;
}
