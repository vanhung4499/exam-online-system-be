package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.entity.NoticeGrade;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface NoticeGradeMapper extends BaseMapper<NoticeGrade> {

    /**
     * Delete the association between notice and class by notice ID.
     * @param noticeIds List of notice IDs
     * @return Number of affected records in the database
     */
    Integer deleteByNoticeIds(List<Integer> noticeIds);

    int addNoticeGrade(Integer noticeId, List<Grade> grades);
}
