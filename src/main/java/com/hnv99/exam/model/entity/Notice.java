package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_notice")
public class Notice implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the notice
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Title of the notice
     */
    private String title;

    /**
     * Image URL
     */
    private String image;

    /**
     * Content of the notice
     */
    private String content;

    /**
     * ID of the user who created the notice
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Creation time of the notice
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Notice{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                ", userId=" + userId +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
