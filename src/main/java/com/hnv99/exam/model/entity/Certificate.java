package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.io.Serializable;
import java.time.LocalDateTime;

@TableName("t_certificate")
public class Certificate implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * ID of the certificate
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Name of the certificate
     */
    private String certificateName;

    /**
     * Background image
     */
    private String image;

    /**
     * Certifying authority
     */
    private String certificationUnit;

    /**
     * Creation time
     */
    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * Logical deletion field
     */
    private Integer isDeleted;

    public Integer getIsDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(Integer isDeleted) {
        this.isDeleted = isDeleted;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCertificateName() {
        return certificateName;
    }

    public void setCertificateName(String certificateName) {
        this.certificateName = certificateName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCertificationUnit() {
        return certificationUnit;
    }

    public void setCertificationUnit(String certificationUnit) {
        this.certificationUnit = certificationUnit;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Certificate{" +
                "id=" + id +
                ", certificateName='" + certificateName + '\'' +
                ", image='" + image + '\'' +
                ", certificationUnit='" + certificationUnit + '\'' +
                ", createTime=" + createTime +
                ", isDeleted=" + isDeleted +
                '}';
    }
}
