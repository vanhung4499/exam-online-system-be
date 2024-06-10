package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_user_exercise_record")
public class UserExerciseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id   User Exercise Record Table
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * User ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Question Bank ID
     */
    private Integer repoId;

    /**
     * Total Number of Questions
     */
    private Integer totalCount;

    /**
     * Number of Questions Attempted
     */
    private Integer exerciseCount;

    /**
     * Exercise Time
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

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    public Integer getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(Integer exerciseCount) {
        this.exerciseCount = exerciseCount;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "UserExerciseRecord{" +
                "id=" + id +
                ", userId=" + userId +
                ", repoId=" + repoId +
                ", totalCount=" + totalCount +
                ", exerciseCount=" + exerciseCount +
                ", createTime=" + createTime +
                '}';
    }
}
