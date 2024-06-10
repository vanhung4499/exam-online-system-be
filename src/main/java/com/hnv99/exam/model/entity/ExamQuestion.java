package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_exam_question")
public class ExamQuestion implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * id
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Exam id Unique
     */
    private Integer examId;

    /**
     * Question id Unique
     */
    private Integer questionId;

    /**
     * Score
     */
    private Integer score;

    /**
     * Sorting
     */
    private Integer sort;

    /**
     * Type
     */
    private Integer type;

    public void setId(Integer id) {
        this.id = id;
    }

    public void setExamId(Integer examId) {
        this.examId = examId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public void setSort(Integer sort) {
        this.sort = sort;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public Integer getExamId() {
        return examId;
    }

    public Integer getQuestionId() {
        return questionId;
    }

    public Integer getScore() {
        return score;
    }

    public Integer getSort() {
        return sort;
    }

    public Integer getType() {
        return type;
    }

    @Override
    public String toString() {
        return "ExamQuestion{" +
                "id=" + id +
                ", examId=" + examId +
                ", questionId=" + questionId +
                ", score=" + score +
                ", sort=" + sort +
                ", type=" + type +
                '}';
    }

    // Getters and setters
}
