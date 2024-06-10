package com.hnv99.exam.service.impl;

import com.hnv99.exam.mapper.RoleMapper;
import com.hnv99.exam.model.entity.Role;
import com.hnv99.exam.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

}
