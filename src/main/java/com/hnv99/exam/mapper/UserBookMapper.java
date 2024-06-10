package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.UserBook;
import com.hnv99.exam.model.vo.userbook.UserPageBookVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

public interface UserBookMapper extends BaseMapper<UserBook> {

    /**
     * Delete the user's error book.
     * @param userIds List of user IDs
     * @return Number of affected records
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Add a list of user books.
     * @param userBookArrayList List of user books
     * @return Number of affected records
     */
    int addUserBookList(List<UserBook> userBookArrayList);

    /**
     * Select page view object.
     * @param page Page information
     * @param examName Exam name
     * @param userId User ID
     * @return Result page
     */
    Page<UserPageBookVO> selectPageVo(Page<UserPageBookVO> page, String examName, Integer userId);
}
