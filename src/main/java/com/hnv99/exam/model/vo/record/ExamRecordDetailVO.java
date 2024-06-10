package com.hnv99.exam.model.vo.record;

import com.hnv99.exam.model.entity.Option;
import lombok.Data;

import java.util.List;

@Data
public class ExamRecordDetailVO {
    // 1. Question stem
    // 2. Options
    // 3. My answer
    // 4. Correct answer
    // 5. Whether it's correct
    // 6. Question analysis

    /**
     * Question stem
     */
    private String title;

    /**
     * Image for the question stem
     */
    private String image;

    /**
     * Options
     */
    private List<Option> option;

    /**
     * My answer
     */
    private String myOption;

    /**
     * Correct answer
     */
    private String rightOption;

    /**
     * Whether the answer is correct
     */
    private Integer isRight;

    /**
     * Question analysis
     */
    private String analyse;

    /**
     * Question type
     */
    private Integer quType;
}