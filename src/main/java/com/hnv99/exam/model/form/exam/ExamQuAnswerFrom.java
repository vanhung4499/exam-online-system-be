package com.hnv99.exam.model.form.exam;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;


@Data
public class ExamQuAnswerFrom {
    private Integer examId;
    private Integer quId;
    /**
     * Answer to the question
     */
    @NotBlank(message = "Answer cannot be blank")
    private String answer;
}
