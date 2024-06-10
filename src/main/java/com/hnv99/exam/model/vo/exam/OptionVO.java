package com.hnv99.exam.model.vo.exam;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OptionVO {
    /**
     * Option ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Question ID
     */
    private Integer quId;

    /**
     * Image URL (0 for incorrect, 1 for correct)
     */
    private String image;

    /**
     * Option content
     */
    @NotBlank(message = "Option content cannot be empty")
    private String content;

    /**
     * Whether it's checked
     */
    private Boolean checkout;

    /**
     * Sorting order
     */
    private Integer sort;
}