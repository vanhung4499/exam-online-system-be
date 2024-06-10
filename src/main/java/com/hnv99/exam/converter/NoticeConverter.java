package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.Notice;
import com.hnv99.exam.model.form.NoticeForm;
import com.hnv99.exam.model.vo.NoticeVO;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel="spring")
public interface NoticeConverter {

    Notice formToEntity(NoticeForm noticeForm);

    Page<NoticeVO> pageEntityToVo(Page<Notice> noticePage);

}
