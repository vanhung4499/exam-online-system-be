package com.hnv99.exam.model.vo.record;

import com.hnv99.exam.model.entity.Option;
import lombok.Data;

import java.util.List;

import lombok.Data;

import java.util.List;

/**
 * View object for detailed exercise record
 */
@Data
public class ExerciseRecordDetailVO {
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
     * Image for the question stem
     */
    private String image;

    /**
     * Question type
     */
    private Integer quType;
}
