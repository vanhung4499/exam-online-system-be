package com.hnv99.exam.model.vo.exam;

import com.hnv99.exam.model.entity.Option;
import lombok.Data;

import java.util.List;

@Data
public class ExamQuCollectVO {

    /**
     * Image
     */
    private String image;

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
     * Question type
     */
    private Integer quType;
}
