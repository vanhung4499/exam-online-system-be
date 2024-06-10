package com.hnv99.exam.model.vo.certificate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MyCertificateVO {
    /**
     * Certificate code
     */
    private String code;

    /**
     * ID of the certificate
     */
    private Integer id;

    /**
     * Name of the certificate
     */
    private String certificateName;

    /**
     * Background image URL
     */
    private String image;

    /**
     * Certification unit
     */
    private String certificationUnit;

    /**
     * Creation time of the certificate
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * Name of the exam
     */
    private String examName;

    /**
     * ID of the exam
     */
    private Integer examId;
}
