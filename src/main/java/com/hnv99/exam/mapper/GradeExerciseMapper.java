package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.GradeExercise;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface GradeExerciseMapper extends BaseMapper<GradeExercise> {

    /**
     * Delete the association table created by the user for exercises available to students
     * @param userIds List of user IDs
     * @return Number of affected records
     */
    Integer deleteByUserIds(List<Integer> userIds);
}
