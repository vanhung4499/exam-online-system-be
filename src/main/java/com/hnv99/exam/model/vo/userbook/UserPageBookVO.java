package com.hnv99.exam.model.vo.userbook;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserPageBookVO {
    private String id;

    /**
     * Title of the exam
     */
    private String title;

    /**
     * Number of errors
     */
    private String numberOfErrors;

    /**
     * Exam ID
     */
    private Integer examId;

    /**
     * Creation time (YYYY-MM-DD HH:mm:ss)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
