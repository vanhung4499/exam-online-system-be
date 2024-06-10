package com.hnv99.exam.model.vo.record;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ExerciseRecordVO {
    /**
     * ID of the exercise
     */
    private Integer id;

    /**
     * ID of the creator
     */
    @TableField(fill = FieldFill.INSERT)
    private Integer userId;

    /**
     * Title of the exercise
     */
    @NotNull(message = "Exercise title cannot be empty")
    private String title;

    /**
     * Creation time of the exercise (YYYY-MM-DD HH:mm:ss)
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
