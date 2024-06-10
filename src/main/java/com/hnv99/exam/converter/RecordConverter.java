package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.Repo;
import com.hnv99.exam.model.vo.record.ExerciseRecordVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface RecordConverter {

    Page<ExerciseRecordVO> pageRepoEntityToVo(Page<Repo> page);

}
