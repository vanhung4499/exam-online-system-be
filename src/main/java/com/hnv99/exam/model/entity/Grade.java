package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("t_grade")
public class Grade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the grade
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Name of the grade
     */
    private String gradeName;

    /**
     * ID of the creator
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Code of the grade
     */
    private String code;

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

    public String getGradeName() {
        return gradeName;
    }

    public void setGradeName(String gradeName) {
        this.gradeName = gradeName;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return "Grade{" +
                "id=" + id +
                ", gradeName='" + gradeName + '\'' +
                ", userId=" + userId +
                ", code='" + code + '\'' +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
