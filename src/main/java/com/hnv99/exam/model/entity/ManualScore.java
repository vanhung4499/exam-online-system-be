package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_manual_score")
public class ManualScore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID   Manual Scoring Table
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Exam record answer ID
     */
    private Integer examQuAnswerId;

    /**
     * Grader ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Score
     */
    private Integer score;

    /**
     * Grading time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getExamQuAnswerId() {
        return examQuAnswerId;
    }

    public void setExamQuAnswerId(Integer examQuAnswerId) {
        this.examQuAnswerId = examQuAnswerId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "ManualScore{" +
                "id=" + id +
                ", examQuAnswerId=" + examQuAnswerId +
                ", userId=" + userId +
                ", score=" + score +
                ", createTime=" + createTime +
                '}';
    }
}
