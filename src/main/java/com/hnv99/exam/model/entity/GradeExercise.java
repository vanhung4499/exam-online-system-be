package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_grade_exercise")
public class GradeExercise implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Exercise ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Repository ID
     */
    private Integer repoId;

    /**
     * Class ID
     */
    private Integer gradeId;

    /**
     * Creator ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRepoId() {
        return repoId;
    }

    public void setRepoId(Integer repoId) {
        this.repoId = repoId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
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
        return "GradeExercise{" +
                "id=" + id +
                ", repoId=" + repoId +
                ", gradeId=" + gradeId +
                ", userId=" + userId +
                ", createTime=" + createTime +
                "}";
    }
}
