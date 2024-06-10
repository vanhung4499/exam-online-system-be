package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.ExamQuestion;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;
import java.util.Map;

public interface ExamQuestionMapper extends BaseMapper<ExamQuestion> {
    int insertQuestion(Integer examId, Integer quType, Integer quScore, List<Map<String, Object>> questionIdsAndSorts);
}
