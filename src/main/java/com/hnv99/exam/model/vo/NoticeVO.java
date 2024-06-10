package com.hnv99.exam.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class NoticeVO {
    private Integer id;

    /**
     * Title of the notice
     */
    private String title;

    /**
     * URL of the image associated with the notice
     */
    private String image;

    /**
     * Content of the notice
     */
    private String content;

    /**
     * ID of the user who created the notice
     */
    private Integer userId;

    /**
     * Creation time of the notice
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;

    /**
     * Name of the user who created the notice
     */
    private String realName;
}
