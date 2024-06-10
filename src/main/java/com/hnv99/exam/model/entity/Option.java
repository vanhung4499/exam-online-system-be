package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.io.Serializable;

@TableName("t_option")
public class Option implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the option answer
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * ID of the question
     */
    private Integer quId;

    /**
     * Indicates whether the option is correct (0 for incorrect, 1 for correct)
     */
    @NotNull(message = "Option correctness (isRight) cannot be null")
    @Min(value = 0, message = "Option correctness (isRight) can only be 0 (incorrect) or 1 (correct)")
    @Max(value = 1, message = "Option correctness (isRight) can only be 0 (incorrect) or 1 (correct)")
    private Integer isRight;

    /**
     * Image URL
     */
    private String image;

    /**
     * Content of the option
     */
    @NotBlank(message = "Option content (content) cannot be blank")
    private String content;

    /**
     * Sort order
     */
    private Integer sort;

    /**
     * Logical deletion field
     */
    private Integer isDeleted;

    // Getters and setters

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuId() {
        return quId;
    }

    public void setQuId(Integer quId) {
        this.quId = quId;
    }

    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Integer getSort() {
        return sort;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    @Override
    public String toString() {
        return "Option{" +
                "id=" + id +
                ", quId=" + quId +
                ", isRight=" + isRight +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", sort=" + sort +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
