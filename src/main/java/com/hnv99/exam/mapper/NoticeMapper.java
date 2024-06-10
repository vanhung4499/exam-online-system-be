package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Notice;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.hnv99.exam.model.vo.NoticeVO;


import java.util.List;

public interface NoticeMapper extends BaseMapper<Notice> {

    int removeNotice(List<Integer> noticeIds);

    /**
     * Delete the notices created by users.
     * @param userIds List of user IDs
     * @return Number of affected records in the database
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Get notice IDs based on user IDs.
     * @param userIds List of user IDs
     * @return Query result
     */
    List<Integer> selectIdsByUserIds(List<Integer> userIds);

    Page<NoticeVO> selectNewNoticePage(Page<NoticeVO> page, Integer userId);
}
