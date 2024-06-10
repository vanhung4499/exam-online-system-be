package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.ManualScore;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface ManualScoreMapper extends BaseMapper<ManualScore> {

    /**
     * Delete subjective questions corrected by users.
     * @param userIds List of user IDs
     * @return Number of affected records
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Batch add correction scores.
     * @param manualScores Input parameter
     * @return Number of affected records
     */
    Integer insertList(List<ManualScore> manualScores);
}
