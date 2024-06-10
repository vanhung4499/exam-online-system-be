package com.hnv99.exam.model.form.answer;

import com.hnv99.exam.common.group.AnswerGroup;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CorrectAnswerForm {

    @NotBlank(message = "The ID of the person being corrected cannot be empty", groups = AnswerGroup.CorrectGroup.class)
    private Integer userId;

    @NotBlank(message = "Exam ID cannot be empty", groups = AnswerGroup.CorrectGroup.class)
    private Integer examId;

    @NotBlank(message = "Question ID cannot be empty", groups = AnswerGroup.CorrectGroup.class)
    private Integer questionId;

    @NotBlank(message = "Score cannot be empty", groups = AnswerGroup.CorrectGroup.class)
    private Integer score;
}
