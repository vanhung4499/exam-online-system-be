package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_user_exams_score")
public class UserExamsScore implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id  User Exam Score Table
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * User ID  Unique
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Exam Paper ID  Unique
     */
    private Integer examId;

    /**
     * Total Time  YYYY-MM-DD hh:mm:ss
     */
    private Integer totalTime;

    /**
     * User Time  YYYY-MM-DD hh:mm:ss
     */
    private Integer userTime;

    /**
     * User Score
     */
    private Integer userScore;

    /**
     * Submission Time  YYYY-MM-DD hh:mm:ss
     */
    private LocalDateTime limitTime;

    /**
     * Screen Switch Count
     */
    private Integer count;

    /**
     * State   0: In Progress 1: Completed
     */
    private Integer state;

    /**
     * Whether Marked: -1: No Short Answer Question, 0: Not Marked, 1: Marked
     */
    private Integer whetherMark;

    public Integer getWhetherMark() {
        return whetherMark;
    }

    public void setWhetherMark(Integer whetherMark) {
        this.whetherMark = whetherMark;
    }

    /**
     * Create Time  YYYY-MM-DD hh:mm:ss
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

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
    public Integer getExamId() {
        return examId;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }
    public Integer getTotalTime() {
        return totalTime;
    }

    public void setTotalTime(Integer totalTime) {
        this.totalTime = totalTime;
    }
    public Integer getUserTime() {
        return userTime;
    }

    public void setUserTime(Integer userTime) {
        this.userTime = userTime;
    }
    public Integer getUserScore() {
        return userScore;
    }

    public void setUserScore(Integer userScore) {
        this.userScore = userScore;
    }
    public LocalDateTime getLimitTime() {
        return limitTime;
    }

    public void setLimitTime(LocalDateTime limitTime) {
        this.limitTime = limitTime;
    }
    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserExamsScore{" +
            "id=" + id +
            ", userId=" + userId +
            ", examId=" + examId +
            ", totalTime=" + totalTime +
            ", userTime=" + userTime +
            ", userScore=" + userScore +
            ", limitTime=" + limitTime +
            ", count=" + count +
            ", state=" + state +
            ", createTime=" + createTime +
        "}";
    }
}
