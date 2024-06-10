package com.hnv99.exam.model.vo.userbook;

import com.hnv99.exam.model.entity.Option;
import lombok.Data;

import java.util.List;

@Data
public class UserExamBookVO {

    /**
     * Question content
     */
    private String content;

    /**
     * Options
     */
    private List<Option> answerList;

    /**
     * Correct answers
     */
    private String rightAnswers;

    /**
     * Question analysis
     */
    private String analyse;

    /**
     * User's response
     */
    private String reply;

}
