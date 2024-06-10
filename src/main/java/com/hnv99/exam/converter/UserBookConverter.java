package com.hnv99.exam.converter;


import com.hnv99.exam.model.entity.UserBook;
import com.hnv99.exam.model.vo.userbook.ReUserExamBookVO;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface UserBookConverter {

    List<ReUserExamBookVO> listEntityToVo(List<UserBook> list);

}
