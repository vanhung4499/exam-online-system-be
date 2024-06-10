package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import jakarta.validation.constraints.NotBlank;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_repo")
public class Repo implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the repository
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * ID of the creator
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Title of the repository
     */
    @NotBlank(message = "Repository title cannot be blank")
    private String title;

    /**
     * Creation time of the repository
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Repo{" +
                "id=" + id +
                ", userId=" + userId +
                ", title='" + title + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
