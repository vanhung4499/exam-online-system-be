package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.time.LocalDate;

@TableName("t_user_daily_login_duration")
public class UserDailyLoginDuration implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * Primary Key ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * User ID
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * Login Date
     */
    @TableField("login_date")
    private LocalDate loginDate;

    /**
     * Accumulated Online Seconds
     */
    @TableField("total_seconds")
    private Integer totalSeconds;


    // Getter and Setter methods

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public LocalDate getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(LocalDate loginDate) {
        this.loginDate = loginDate;
    }

    public Integer getTotalSeconds() {
        return totalSeconds;
    }

    public void setTotalSeconds(Integer totalSeconds) {
        this.totalSeconds = totalSeconds;
    }
}
