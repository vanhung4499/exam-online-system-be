package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_user_book")
public class UserBook implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID   Error Book
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Exam ID  Unique
     */
    private Integer examId;

    /**
     * User ID    Unique
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Question ID    Unique
     */
    private Integer quId;

    /**
     * Creation Time    YYYY-MM-DD hh:mm:ss
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }
    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public Integer getQuId() {
        return quId;
    }

    public void setQuId(Integer quId) {
        this.quId = quId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserBook{" +
                "id=" + id +
                ", examId=" + examId +
                ", userId=" + userId +
                ", quId=" + quId +
                ", createTime=" + createTime +
                "}";
    }
}
