package com.hnv99.exam.model.vo.userbook;

import com.hnv99.exam.model.entity.Option;
import lombok.Data;

import java.util.List;

@Data
public class BookOneQuVO {
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
     * Question type
     */
    private Integer quType;

    /**
     * Answer content
     */
    private List<Option> answerList;
}
