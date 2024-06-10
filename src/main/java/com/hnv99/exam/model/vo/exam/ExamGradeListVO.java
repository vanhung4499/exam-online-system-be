package com.hnv99.exam.model.vo.exam;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExamGradeListVO {
    /**
     * ID of the exam
     */
    private Integer id;

    /**
     * Name of the exam
     */
    private String title;

    /**
     * Duration of the exam (in minutes)
     */
    private Integer examDuration;

    /**
     * Passing score for the exam
     */
    private Integer passedScore;

    /**
     * Total score of the exam
     */
    private Integer grossScore;

    /**
     * Maximum number of allowed screen switches
     */
    private Integer maxCount;

    /**
     * ID of the creator
     */
    private Integer userId;

    /**
     * ID of the certificate associated with the exam
     */
    private Integer certificateId;

    /**
     * Number of single-choice questions
     */
    private Integer radioCount;

    /**
     * Score for single-choice questions (stored as score * 100, displayed as score / 100)
     */
    private Integer radioScore;

    /**
     * Number of multiple-choice questions
     */
    private Integer multiCount;

    /**
     * Score for multiple-choice questions (stored as score * 100, displayed as score / 100)
     */
    private Integer multiScore;

    /**
     * Number of true/false questions
     */
    private Integer judgeCount;

    /**
     * Score for true/false questions (stored as score * 100, displayed as score / 100)
     */
    private Integer judgeScore;

    /**
     * Number of short answer questions
     */
    private Integer saqCount;

    /**
     * Score for short answer questions (stored as score * 100, displayed as score / 100)
     */
    private Integer saqScore;

    /**
     * Start time of the exam (YYYY-MM-DD HH:mm:ss)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime startTime;

    /**
     * End time of the exam (YYYY-MM-DD HH:mm:ss)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endTime;

    /**
     * Creation time of the exam (YYYY-MM-DD HH:mm:ss)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
