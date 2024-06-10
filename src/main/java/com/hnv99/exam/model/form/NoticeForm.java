package com.hnv99.exam.model.form;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class NoticeForm {

    /**
     * ID of the notice
     */
    private Integer id;

    /**
     * Title of the notice
     */
    @NotBlank
    private String title;

    /**
     * Image URL
     */
    private String image;

    /**
     * Content of the notice
     */
    @NotBlank
    private String content;

    /**
     * Unique user ID of the creator
     */
    private Integer userId;
}

