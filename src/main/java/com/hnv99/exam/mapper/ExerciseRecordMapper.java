package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.ExerciseRecord;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author WeiJin
 * @since 2024-03-21
 */
public interface ExerciseRecordMapper extends BaseMapper<ExerciseRecord> {

    /**
     * 删除用户练习作答记录
     * @param userIds 用户id列表
     * @return 影响记录数
     */
    Integer deleteByUserIds(List<Integer> userIds);
}
