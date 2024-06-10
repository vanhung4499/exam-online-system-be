package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Option;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface OptionMapper extends BaseMapper<Option> {

    /**
     * Batch insert options.
     * @param options List of options
     * @return Number of affected records in the database
     */
    Integer insertBatch(List<Option> options);

    /**
     * Batch delete options based on the list of question IDs.
     * @param quIdList List of question IDs
     * @return Number of affected records in the table
     */
    Integer deleteBatchByQuIds(List<Integer> quIdList);

    /**
     * Get all options by question ID.
     * @param id Question ID
     * @return Result set
     */
    List<Option> selectAllByQuestionId(Integer id);

    /**
     * Get all options by question ID, excluding correctness.
     * @param id Question ID
     * @return Result set
     */
    List<Option> selectByQuestionId(Integer id);

    /**
     * Get the count of correct options by option IDs.
     * @param optionIds List of option IDs
     * @return Count of correct options
     */
    Integer selectRightCountByIds(List<Integer> optionIds);
}
