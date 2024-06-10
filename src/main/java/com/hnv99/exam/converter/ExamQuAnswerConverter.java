package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.ExamQuAnswer;
import com.hnv99.exam.model.form.examquanswer.ExamQuAnswerAddForm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring")
public interface ExamQuAnswerConverter {

    @Mapping(target = "questionId", source = "quId")
    ExamQuAnswer formToEntity(ExamQuAnswerAddForm examQuAnswerAddForm);

}
