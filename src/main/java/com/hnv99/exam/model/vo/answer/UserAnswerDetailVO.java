package com.hnv99.exam.model.vo.answer;

import lombok.Data;

@Data
public class UserAnswerDetailVO {
    private Integer quId;
    private Integer userId;
    private Integer examId;
    private String quTitle;
    private String quImg;
    private String answer;
    private String refAnswer;
    private Integer correctScore;
    private Integer totalScore;

}
