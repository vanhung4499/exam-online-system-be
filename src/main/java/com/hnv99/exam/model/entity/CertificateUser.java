package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * Certificate and user relationship entity
 */
@TableName("t_certificate_user")
public class CertificateUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * User ID
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Exam ID
     */
    private Integer examId;

    /**
     * Certificate code
     */
    private String code;

    /**
     * Certificate ID
     */
    private Integer certificateId;

    /**
     * Award time (YYYY-MM-DD hh:mm:ss)
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
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    public Integer getCertificateId() {
        return certificateId;
    }

    public void setCertificateId(Integer certificateId) {
        this.certificateId = certificateId;
    }
    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }


    @Override
    public String toString() {
        return "CertificateUser{" +
                "id=" + id +
                ", userId=" + userId +
                ", examId=" + examId +
                ", code='" + code + '\'' +
                ", certificateId=" + certificateId +
                ", createTime=" + createTime +
                '}';
    }
}
