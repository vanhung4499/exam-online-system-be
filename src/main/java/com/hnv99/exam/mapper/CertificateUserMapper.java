package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.CertificateUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface CertificateUserMapper extends BaseMapper<CertificateUser> {

    /**
     * Batch delete certificates associated with a list of user IDs
     * @param userIds List of user IDs
     * @return Number of affected database records
     */
    Integer deleteByUserIds(List<Integer> userIds);
}
