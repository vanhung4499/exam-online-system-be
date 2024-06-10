package com.hnv99.exam.service.impl;

import com.hnv99.exam.mapper.UserDailyLoginDurationMapper;
import com.hnv99.exam.model.entity.UserDailyLoginDuration;
import com.hnv99.exam.service.UserDailyLoginDurationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;


@Service
public class UserDailyLoginDurationServiceImpl extends ServiceImpl<UserDailyLoginDurationMapper, UserDailyLoginDuration> implements UserDailyLoginDurationService {
}
