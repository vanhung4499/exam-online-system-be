package com.hnv99.exam.model.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GradeForm {
    /**
     * Grade name
     */
    @NotBlank
    private String gradeName;

    /**
     * Grade code
     */
    private String code;
}
