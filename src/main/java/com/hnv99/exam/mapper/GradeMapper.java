package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.vo.GradeVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface GradeMapper extends BaseMapper<Grade> {
    List<Integer> selectIdsByUserIds(List<Integer> userIds);

    Integer deleteByUserId(List<Integer> userIds);

    Page<GradeVO> selectGradePage(Page<GradeVO> page, Integer userId , String gradeName, Integer role);
}
