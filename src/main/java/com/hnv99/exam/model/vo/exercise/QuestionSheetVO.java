package com.hnv99.exam.model.vo.exercise;

import lombok.Data;

@Data
public class QuestionSheetVO {
    private Integer quId;
    private Integer quType;
    private Integer repoId;
    private Integer exercised;
    private Integer isRight;
}
