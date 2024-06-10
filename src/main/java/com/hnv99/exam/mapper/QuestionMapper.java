package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.Question;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.model.vo.exercise.QuestionSheetVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface QuestionMapper extends BaseMapper<Question> {

    /**
     * Paginate and retrieve question information.
     *
     * @param page    Pagination information
     * @param content Fuzzy search for question content
     * @param repoId  Repository ID
     * @param type    Question type
     * @param userId  User ID
     * @return Result set
     */
    IPage<QuestionVO> pagingQuestion(IPage<QuestionVO> page, String content, Integer repoId, Integer type, Integer userId);

    /**
     * Get details of a single question by question ID.
     * @param id Question ID
     * @return Result set
     */
    QuestionVO selectSingle(Integer id);

    /**
     * Delete questions added by users.
     * @param userIds List of user IDs
     * @return Number of affected records
     */
    Integer deleteByUserIds(List<Integer> userIds);

    /**
     * Get the list of question IDs created by users.
     * @param userIds List of user IDs
     * @return Query result
     */
    List<Integer> selectIdsByUserIds(List<Integer> userIds);

    /**
     * Get question IDs and check if the user has answered the question.
     * @param repoId Repository ID
     * @param quType Question type
     * @param userId User ID
     * @return Query result
     */
    List<QuestionSheetVO> selectQuestionSheet(Integer repoId, Integer quType, Integer userId);

    /**
     * Get detailed information of a question by question ID.
     * @param id Question ID
     * @return Result set
     */
    QuestionVO selectDetail(Integer id);

    void deleteBatchIdsQu(List<Integer> list);
}
