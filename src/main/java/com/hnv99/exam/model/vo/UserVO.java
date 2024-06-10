package com.hnv99.exam.model.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;


@Data
public class UserVO {

    private Integer id;
    private String userName;
    private String realName;
    private String roleName;
    private String password;
    private String avatar;
    private String gradeName;
    /**
     * Grade Creator ID
     */
    private Integer userId;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
    private String status;

}

