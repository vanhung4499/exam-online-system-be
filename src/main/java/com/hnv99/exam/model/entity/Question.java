package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_question")
public class Question implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the question
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Type of the question
     */
    private Integer quType;

    /**
     * Image associated with the question
     */
    private String image;

    /**
     * Content of the question
     */
    private String content;

    /**
     * Creation time of the question
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * Analysis of the question
     */
    private String analysis;

    /**
     * ID of the repository the question belongs to
     */
    private Integer repoId;

    /**
     * ID of the user who created the question
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Logical deletion field
     */
    private Integer isDeleted;

    // Getters and setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getQuType() {
        return quType;
    }

    public void setQuType(Integer quType) {
        this.quType = quType;
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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public String getAnalysis() {
        return analysis;
    }

    public void setAnalysis(String analysis) {
        this.analysis = analysis;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", quType=" + quType +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", createTime=" + createTime +
                ", analysis='" + analysis + '\'' +
                ", repoId=" + repoId +
                ", userId=" + userId +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

