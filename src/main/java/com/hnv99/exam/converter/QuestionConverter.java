package com.hnv99.exam.converter;

import com.hnv99.exam.model.entity.Question;
import com.hnv99.exam.model.form.question.QuestionForm;
import com.hnv99.exam.model.vo.exercise.QuestionSheetVO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Mapper(componentModel = "spring")
public interface QuestionConverter {

    @Mapping(target = "repoId",source = "repoId")
    Question fromToEntity(QuestionForm questionFrom);

    List<QuestionSheetVO> listEntityToVO(List<Question> questions);

    @Mapping(target = "quId",source = "id")
    QuestionSheetVO entityToVO(Question question);

}
