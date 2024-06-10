package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_exam")
public class Exam implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the exam
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Name of the exam
     */
    private String title;

    /**
     * Duration of the exam
     */
    private Integer examDuration;

    /**
     * Passing score
     */
    private Integer passedScore;

    /**
     * Total score
     */
    private Integer grossScore;

    /**
     * Maximum number of screen switches
     */
    private Integer maxCount;

    /**
     * ID of the creator
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * ID of the certificate
     */
    private Integer certificateId;

    /**
     * Number of single choice questions
     */
    private Integer radioCount;

    /**
     * Score for single choice questions, stored in the database as *100, input and displayed normally as /100
     */
    private Integer radioScore;

    /**
     * Number of multiple choice questions
     */
    private Integer multiCount;

    /**
     * Score for multiple choice questions, stored in the database as *100, input and displayed normally as /100
     */
    private Integer multiScore;

    /**
     * Number of true/false questions
     */
    private Integer judgeCount;

    /**
     * Score for true/false questions, stored in the database as *100, input and displayed normally as /100
     */
    private Integer judgeScore;

    /**
     * Number of short answer questions
     */
    private Integer saqCount;

    /**
     * Score for short answer questions, stored in the database as *100, input and displayed normally as /100
     */
    private Integer saqScore;

    /**
     * Start time, format: YYYY-MM-DD hh:mm:ss
     */
    private LocalDateTime startTime;

    /**
     * End time, format: YYYY-MM-DD hh:mm:ss
     */
    private LocalDateTime endTime;

    /**
     * Creation time, format: YYYY-MM-DD hh:mm:ss
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getExamDuration() {
        return examDuration;
    }

    public void setExamDuration(Integer examDuration) {
        this.examDuration = examDuration;
    }

    public Integer getPassedScore() {
        return passedScore;
    }

    public void setPassedScore(Integer passedScore) {
        this.passedScore = passedScore;
    }

    public Integer getGrossScore() {
        return grossScore;
    }

    public void setGrossScore(Integer grossScore) {
        this.grossScore = grossScore;
    }

    public Integer getMaxCount() {
        return maxCount;
    }

    public void setMaxCount(Integer maxCount) {
        this.maxCount = maxCount;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }

    public Integer getRadioCount() {
        return radioCount;
    }

    public void setRadioCount(Integer radioCount) {
        this.radioCount = radioCount;
    }

    public Integer getRadioScore() {
        return radioScore;
    }

    public void setRadioScore(Integer radioScore) {
        this.radioScore = radioScore;
    }

    public Integer getMultiCount() {
        return multiCount;
    }

    public void setMultiCount(Integer multiCount) {
        this.multiCount = multiCount;
    }

    public Integer getMultiScore() {
        return multiScore;
    }

    public void setMultiScore(Integer multiScore) {
        this.multiScore = multiScore;
    }

    public Integer getJudgeCount() {
        return judgeCount;
    }

    public void setJudgeCount(Integer judgeCount) {
        this.judgeCount = judgeCount;
    }

    public Integer getJudgeScore() {
        return judgeScore;
    }

    public void setJudgeScore(Integer judgeScore) {
        this.judgeScore = judgeScore;
    }

    public Integer getSaqCount() {
        return saqCount;
    }

    public void setSaqCount(Integer saqCount) {
        this.saqCount = saqCount;
    }

    public Integer getSaqScore() {
        return saqScore;
    }

    public void setSaqScore(Integer saqScore) {
        this.saqScore = saqScore;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    @Override
    public String toString() {
        return "Exam{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", examDuration=" + examDuration +
                ", passedScore=" + passedScore +
                ", grossScore=" + grossScore +
                ", maxCount=" + maxCount +
                ", userId=" + userId +
                ", certificateId=" + certificateId +
                ", radioCount=" + radioCount +
                ", radioScore=" + radioScore +
                ", multiCount=" + multiCount +
                ", multiScore=" + multiScore +
                ", judgeCount=" + judgeCount +
                ", judgeScore=" + judgeScore +
                ", saqCount=" + saqCount +
                ", saqScore=" + saqScore +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}

