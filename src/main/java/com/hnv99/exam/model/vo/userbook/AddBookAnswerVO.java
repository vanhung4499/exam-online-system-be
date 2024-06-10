package com.hnv99.exam.model.vo.userbook;

import lombok.Data;

@Data
public class AddBookAnswerVO {
    // Indicates if the answer is correct
    private Integer correct;
    // Returns the correct answers
    private String rightAnswers;
    // Returns the analysis
    private String analysis;
}
