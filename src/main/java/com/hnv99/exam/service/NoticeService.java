package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.Notice;
import com.hnv99.exam.model.form.NoticeForm;
import com.hnv99.exam.model.vo.NoticeVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

public interface NoticeService extends IService<Notice> {

    /**
     * Add a notice
     *
     * @param noticeForm notice information
     * @return result
     */
    Result<String> addNotice(NoticeForm noticeForm);

    /**
     * Delete a notice
     *
     * @param ids notice IDs
     * @return result
     */
    Result<String> deleteNotice(String ids);

    /**
     * Update a notice
     *
     * @param id notice ID
     * @param noticeForm updated notice information
     * @return result
     */
    Result<String> updateNotice(String id, NoticeForm noticeForm);

    /**
     * Paginate notices for teachers
     *
     * @param pageNum page number
     * @param pageSize page size
     * @param title notice title
     * @return result
     */
    Result<IPage<NoticeVO>> getNotice(Integer pageNum, Integer pageSize, String title);

    /**
     * Retrieve the latest notices for users
     *
     * @param pageNum page number
     * @param pageSize page size
     * @return result
     */
    Result<IPage<NoticeVO>> getNewNotice(Integer pageNum, Integer pageSize);
}
