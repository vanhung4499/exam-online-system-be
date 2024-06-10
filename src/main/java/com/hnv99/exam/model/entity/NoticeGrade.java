package com.hnv99.exam.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_notice_grade")
public class NoticeGrade implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID of the notice grade association
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * Notice ID
     */
    private Integer noticeId;

    /**
     * Grade ID
     */
    private Integer gradeId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNoticeId() {
        return noticeId;
    }

    public void setNoticeId(Integer noticeId) {
        this.noticeId = noticeId;
    }

    public Integer getGradeId() {
        return gradeId;
    }

    public void setGradeId(Integer gradeId) {
        this.gradeId = gradeId;
    }

    @Override
    public String toString() {
        return "NoticeGrade{" +
                "id=" + id +
                ", noticeId=" + noticeId +
                ", gradeId=" + gradeId +
                "}";
    }
}
