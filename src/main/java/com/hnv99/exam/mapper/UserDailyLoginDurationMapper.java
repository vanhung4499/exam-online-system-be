package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.UserDailyLoginDuration;
import com.hnv99.exam.model.vo.stat.DailyVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;


public interface UserDailyLoginDurationMapper extends BaseMapper<UserDailyLoginDuration> {
    List<DailyVO>  getDaily(Integer userId);
}
