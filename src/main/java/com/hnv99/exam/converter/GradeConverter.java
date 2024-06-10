package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.Grade;
import com.hnv99.exam.model.form.GradeForm;
import com.hnv99.exam.model.vo.GradeVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel="spring")
public interface GradeConverter {

    Page<GradeVO> pageEntityToVo(Page<Grade> page);

    Grade formToEntity(GradeForm gradeForm);

    List<GradeVO> listEntityToVo(List<Grade> page);

}
