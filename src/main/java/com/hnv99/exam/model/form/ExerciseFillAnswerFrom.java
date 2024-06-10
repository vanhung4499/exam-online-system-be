package com.hnv99.exam.model.form;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ExerciseFillAnswerFrom {
    @NotNull(message = "Repo ID cannot be null")
    private Integer repoId;

    @NotNull(message = "Question ID cannot be null")
    private Integer quId;

    @NotBlank(message = "Answer content cannot be blank")
    private String answer;

    @NotNull(message = "Question type cannot be null")
    @Min(value = 1, message = "Question type should be at least 1")
    @Max(value = 4, message = "Question type should be at most 4")
    private Integer quType;
}
