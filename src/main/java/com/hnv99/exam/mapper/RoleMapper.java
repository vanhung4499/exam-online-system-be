package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectCodeById(Integer roleId);
}
