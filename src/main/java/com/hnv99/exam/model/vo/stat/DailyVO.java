package com.hnv99.exam.model.vo.stat;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DailyVO {
    private Integer id;

    /**
     * User ID
     */
    private Integer userId;

    /**
     * Login date
     */
    private LocalDate loginDate;

    /**
     * Total online seconds
     */
    private Integer totalSeconds;
}
