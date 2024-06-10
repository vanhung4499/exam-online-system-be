package com.hnv99.exam.model.vo.answer;

import lombok.Data;

@Data
public class AnswerExamVO {
    /**
     * Exam ID
     */
    private Integer examId;

    /**
     * Exam title
     */
    private String examTitle;

    /**
     * Indicates if the exam needs to be marked
     */
    private Integer neededMark;

    /**
     * Total number of students in the class
     */
    private Integer classSize;

    /**
     * Number of applicants
     */
    private Integer numberOfApplicants;

    /**
     * Number of papers that have been corrected
     */
    private Integer correctedPaper;
}
