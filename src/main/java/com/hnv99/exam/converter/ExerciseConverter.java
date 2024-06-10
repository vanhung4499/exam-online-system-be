package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.ExerciseRecord;
import com.hnv99.exam.model.form.ExerciseFillAnswerFrom;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.model.vo.exercise.AnswerInfoVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel="spring")
public interface ExerciseConverter {
    @Mappings({
            @Mapping(source = "quId",target = "questionId"),
            @Mapping(source = "quType",target = "questionType")
    })
    ExerciseRecord fromToEntity(ExerciseFillAnswerFrom exerciseFillAnswerFrom);

    AnswerInfoVO quVOToAnswerInfoVO(QuestionVO questionVO);
}
