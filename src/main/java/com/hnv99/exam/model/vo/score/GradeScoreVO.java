package com.hnv99.exam.model.vo.score;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GradeScoreVO {
    private Integer id;
    private Integer examId;
    private Integer gradeId;
    private Integer passedScore;
    private String examTitle;
    private String gradeName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * Average score
     */
    private Integer avgScore;

    /**
     * Highest score
     */
    private Integer maxScore;

    /**
     * Lowest score
     */
    private Integer minScore;

    /**
     * Number of participants
     */
    private Integer attendNum;

    /**
     * Number of absentees
     */
    private Integer absentNum;

    /**
     * Number of passers
     */
    private Integer passedNum;

    /**
     * Total number of participants
     */
    private Integer totalNum;

    /**
     * Passing rate
     */
    private Double passingRate;
}
