package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.Repo;
import com.hnv99.exam.model.vo.repo.RepoVO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface RepoConverter {

    List<RepoVO> listEntityToVo(List<Repo> list);

}
