package com.hnv99.exam.model.form.exam;

import jakarta.validation.constraints.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ExamUpdateForm {
    /**
     * Exam title
     */
    @NotBlank(message = "Exam title cannot be blank")
    @Size(min = 3, max = 20, message = "Please enter a title between 3 and 20 characters")
    private String title;

    /**
     * Exam duration
     */
    @NotNull(message = "Please set the exam duration, in minutes")
    @Min(value = 0, message = "Please set a duration greater than 0")
    private Integer examDuration;

    /**
     * Maximum screen switch count
     */
    private Integer maxCount;

    /**
     * Passing score
     */
    @Min(value = 0, message = "Passing score must be greater than 0")
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
     * Exam classes
     */
    // @NotBlank(message = "Class cannot be blank")
    // @Pattern(regexp = "^\\d+(,\\d+)*$|^\\d+$", message = "Invalid class format, please use 1,2,3,4... and at least one class ID")
    private String gradeIds;

    /**
     * Certificate ID
     */
    private Integer certificateId;

    /**
     * Single choice question score
     */
    @NotNull(message = "Single choice question score cannot be blank")
    @Min(value = 0)
    private Integer radioScore;

    /**
     * Multiple choice question score
     */
    @NotNull(message = "Multiple choice question score cannot be blank")
    @Min(value = 0)
    private Integer multiScore;

    /**
     * True/false question score
     */
    @NotNull(message = "True/false question score cannot be blank")
    @Min(value = 0)
    private Integer judgeScore;

    /**
     * Short answer question score
     */
    @NotNull(message = "Short answer question score cannot be blank")
    @Min(value = 0)
    private Integer saqScore;
}
