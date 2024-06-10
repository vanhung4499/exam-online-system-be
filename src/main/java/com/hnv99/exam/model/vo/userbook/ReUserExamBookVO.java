package com.hnv99.exam.model.vo.userbook;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReUserExamBookVO {
    private static final long serialVersionUID = 1L;

    /**
     * ID of the error book
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Exam ID (unique)
     */
    private Integer examId;

    /**
     * User ID (unique)
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Question ID (unique)
     */
    private Integer quId;

    /**
     * Creation time (format: YYYY-MM-DD hh:mm:ss)
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
}
