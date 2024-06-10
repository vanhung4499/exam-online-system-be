package com.hnv99.exam.model.vo.exam;

import lombok.Data;

import java.util.List;

@Data
public class ExamQuDetailVO {
    private static final long serialVersionUID = 1L;

    /**
     * Image
     */
    private String image;

    /**
     * Question content
     */
    private String content;

    /**
     * Answer content
     */
    private List<OptionVO> answerList;

    /**
     * Question type
     */
    private Integer quType;

    /**
     * Sorting order
     */
    private Integer sort;
}
