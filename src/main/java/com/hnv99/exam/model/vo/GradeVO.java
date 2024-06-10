package com.hnv99.exam.model.vo;


import lombok.Data;

@Data
public class GradeVO {
    private static final long serialVersionUID = 1L;

    /**
     * id of the class table
     */
    private Integer id;

    /**
     * Class name
     */
    private String gradeName;

    /**
     * Creator's ID
     */
    private Integer userId;

    /**
     * Creator's name
     */
    private String userName;

    /**
     * Class code
     */
    private String code;

    /**
     * Number of students in the class
     */
    private Integer gradeCount;
}
