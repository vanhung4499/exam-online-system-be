package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

/**
 * Exam question answer entity
 */
@TableName("t_exam_qu_answer")
public class ExamQuAnswer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Answer ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * User ID
     */
    private Integer userId;

    /**
     * Exam ID
     */
    private Integer examId;

    /**
     * Question ID
     */
    private Integer questionId;

    /**
     * Question type
     */
    private Integer questionType;

    /**
     * Answer ID (used for objective questions; for multiple-choice questions, IDs are separated by commas)
     */
    private String answerId;

    /**
     * Answer content (used for subjective questions)
     */
    private String answerContent;

    /**
     * Whether the answer is checked (0: not checked, 1: checked)
     */
    private Integer checkout;

    /**
     * Whether the question is marked (0: not marked, 1: marked)
     */
    private Integer isSign;

    /**
     * Whether the answer is correct (used for objective questions; 0: incorrect, 1: correct)
     */
    private Integer isRight;

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
    public Integer getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Integer questionId) {
        this.questionId = questionId;
    }
    public Integer getQuestionType() {
        return questionType;
    }

    public void setQuestionType(Integer questionType) {
        this.questionType = questionType;
    }
    public String getAnswerId() {
        return answerId;
    }

    public void setAnswerId(String answerId) {
        this.answerId = answerId;
    }
    public String getAnswerContent() {
        return answerContent;
    }

    public void setAnswerContent(String answerContent) {
        this.answerContent = answerContent;
    }
    public Integer getCheckout() {
        return checkout;
    }

    public void setCheckout(Integer checkout) {
        this.checkout = checkout;
    }
    public Integer getIsSign() {
        return isSign;
    }

    public void setIsSign(Integer isSign) {
        this.isSign = isSign;
    }
    public Integer getIsRight() {
        return isRight;
    }

    public void setIsRight(Integer isRight) {
        this.isRight = isRight;
    }

    @Override
    public String toString() {
        return "ExamQuAnswer{" +
                "id=" + id +
                ", userId=" + userId +
                ", examId=" + examId +
                ", questionId=" + questionId +
                ", questionType=" + questionType +
                ", answerId='" + answerId + '\'' +
                ", answerContent='" + answerContent + '\'' +
                ", checkout=" + checkout +
                ", isSign=" + isSign +
                ", isRight=" + isRight +
                '}';
    }
}
