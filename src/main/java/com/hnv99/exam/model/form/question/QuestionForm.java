package com.hnv99.exam.model.form.question;

import com.hnv99.exam.common.group.QuestionGroup;
import com.hnv99.exam.model.entity.Option;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class QuestionForm {

    private Integer id;

    /**
     * Question type
     */
    @NotNull(message = "Question type (quType) cannot be null", groups = QuestionGroup.QuestionAddGroup.class)
    @Min(value = 1, message = "Question type (quType) must be one of: 1 Single choice, 2 Multiple choice, 3 True or false, 4 Short answer", groups = QuestionGroup.QuestionAddGroup.class)
    @Max(value = 4, message = "Question type (quType) must be one of: 1 Single choice, 2 Multiple choice, 3 True or false, 4 Short answer", groups = QuestionGroup.QuestionAddGroup.class)
    private Integer quType;

    /**
     * Question image
     */
    private String image;
    private String analysis;

    /**
     * Stem
     */
    @NotBlank(message = "Stem (content) cannot be blank", groups = QuestionGroup.QuestionAddGroup.class)
    private String content;

    /**
     * Creation time
     */
    private LocalDateTime createTime;

    @NotNull(message = "Repo ID (repoId) cannot be null", groups = QuestionGroup.QuestionAddGroup.class)
    private Integer repoId;

    /**
     * Option list
     */
//    @NotNull(message = "Option list (options) cannot be null", groups = QuestionGroup.QuestionAddGroup.class)
//    @Size(min = 2, message = "Option list (options) must have at least two options", groups = QuestionGroup.QuestionAddGroup.class)
//    @Valid
    private List<Option> options;
}
