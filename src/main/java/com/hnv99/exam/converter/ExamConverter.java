package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.Exam;
import com.hnv99.exam.model.entity.ExamQuestion;
import com.hnv99.exam.model.entity.Option;
import com.hnv99.exam.model.form.exam.ExamAddForm;
import com.hnv99.exam.model.form.exam.ExamUpdateForm;
import com.hnv99.exam.model.vo.exam.*;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Mapper(componentModel="spring")
public interface ExamConverter {

    Page<ExamVO> pageEntityToVo(Page<Exam> examPage);

    Exam  formToEntity(ExamUpdateForm examUpdateForm);

    Exam  formToEntity(ExamAddForm examAddForm);

    List<ExamDetailRespVO> listEntityToExamDetailRespVO(List<ExamQuestion> examQuestion);

    ExamDetailVO examToExamDetailVO(Exam exam);

    ExamGradeListVO entityToExamGradeListVO(Exam exam);

    ExamQuestionVO examQuestionEntityToVO(ExamQuestion examQuestion);

    List<ExamQuestionVO> examQuestionListEntityToVO(List<ExamQuestion> examQuestion);

    List<OptionVO> opListEntityToVO(List<Option> examQuestion);
}
