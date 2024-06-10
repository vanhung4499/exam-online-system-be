package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;

@TableName("t_exercise_record")
public class ExerciseRecord implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Repository ID
     */
    private Integer repoId;

    /**
     * Question ID
     */
    private Integer questionId;

    /**
     * User ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Subjective answer
     */
    private String answer;

    /**
     * Question type
     */
    private Integer questionType;

    /**
     * Objective answer set
     * Used for multiple-choice questions; multiple choice IDs are separated by ","
     */
    private String options;

    /**
     * Whether the objective question is correct
     */
    private Integer isRight;

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
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }
    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
    public String getOptions() {
        return options;
    }

    public void setOptions(String options) {
        this.options = options;
    }
    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    @Override
    public String toString() {
        return "ExerciseRecord{" +
                "id=" + id +
                ", repoId=" + repoId +
                ", questionId=" + questionId +
                ", userId=" + userId +
                ", answer=" + answer +
                ", questionType=" + questionType +
                ", options=" + options +
                ", isRight=" + isRight +
                "}";
    }
}
