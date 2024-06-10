package com.hnv99.exam.model.form.exam;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
@Data
public class ExamAddForm {
    /**
     * Exam title
     */
    @NotBlank(message = "Exam title cannot be blank")
    @Size(min = 3, max = 20, message = "Please enter an exam title between 3 and 20 characters")
    private String title;

    /**
     * Exam duration
     */
    @NotNull(message = "Please set the exam duration, in minutes")
    @Min(value=0,message = "Please set a positive exam duration")
    private Integer examDuration;

    /**
     * Maximum screen switching count
     */
    private Integer maxCount;

    /**
     * Passing score
     */
    @Min(value=0,message = "Passing score must be greater than 0")
    @NotNull(message = "Passing score cannot be blank")
    private Integer passedScore;

    /**
     * Start time
     */
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime startTime;

    /**
     * End time
     */
    @Future(message = "End time must be a future date")
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm:ss")
    private LocalDateTime endTime;

    /**
     * Exam grades
     */
    @NotBlank(message = "Grade cannot be blank")
    @Pattern(regexp = "^\\d+(,\\d+)*$|^\\d+$", message = "Incorrect grade parameter. Please use the format 1,2,3,4... and include at least one grade ID")
    private String gradeIds;

    /**
     * Repository ID
     */
    @NotNull(message = "Repository cannot be blank")
    private Integer repoId;

    /**
     * Certificate ID
     */
    private Integer certificateId;

    /**
     * Number of single-choice questions
     */
    @NotNull(message = "Number of single-choice questions cannot be blank")
    @Min(value = 0)
    private Integer radioCount;

    /**
     * Score per single-choice question
     */
    @NotNull(message = "Score per single-choice question cannot be blank")
    @Min(value = 0)
    private Integer radioScore;

    /**
     * Number of multiple-choice questions
     */
    @NotNull(message = "Number of multiple-choice questions cannot be blank")
    @Min(value = 0)
    private Integer multiCount;

    /**
     * Score per multiple-choice question
     */
    @NotNull(message = "Score per multiple-choice question cannot be blank")
    @Min(value = 0)
    private Integer multiScore;

    /**
     * Number of true/false questions
     */
    @NotNull(message = "Number of true/false questions cannot be blank")
    @Min(value = 0)
    private Integer judgeCount;

    /**
     * Score per true/false question
     */
    @NotNull(message = "Score per true/false question cannot be blank")
    @Min(value = 0)
    private Integer judgeScore;

    /**
     * Number of short-answer questions
     */
    @NotNull(message = "Number of short-answer questions cannot be blank")
    @Min(value = 0)
    private Integer saqCount;

    /**
     * Score per short-answer question
     */
    @NotNull(message = "Score per short-answer question cannot be blank")
    @Min(value = 0)
    private Integer saqScore;
}
