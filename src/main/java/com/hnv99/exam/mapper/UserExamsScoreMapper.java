package com.hnv99.exam.mapper;

import com.hnv99.exam.model.entity.UserExamsScore;
import com.hnv99.exam.model.vo.answer.UncorrectedUserVO;
import com.hnv99.exam.model.vo.score.ExportScoreVO;
import com.hnv99.exam.model.vo.score.GradeScoreVO;
import com.hnv99.exam.model.vo.score.UserScoreVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

public interface UserExamsScoreMapper extends BaseMapper<UserExamsScore> {

    /**
     * Analysis of exam class user scores.
     * @param page Page information
     * @param gradeId Class ID
     * @param examTitle Exam title
     * @param userId User ID
     * @param roleId Role ID
     * @return Result
     */
    IPage<GradeScoreVO> scoreStatistics(IPage<GradeScoreVO> page, Integer gradeId, String examTitle, Integer userId, Integer roleId);

    /**
     * Score pagination query.
     *
     * @param page     Page information
     * @param gradeId  Class ID
     * @param examId   Exam ID
     * @param realName Real name
     * @return Query result set
     */
    IPage<UserScoreVO> pagingScore(IPage<UserScoreVO> page, Integer gradeId, Integer examId, String realName);

    /**
     * Get the number of applicants.
     *
     * @param examId Exam ID
     * @return Query result
     */
    Integer getNumberOfApplicants(Integer examId);

    /**
     * Get corrected papers.
     *
     * @param examId Exam ID
     * @return Query result
     */
    Integer getCorrectedPaper(Integer examId);

    /**
     * Get scores.
     *
     * @param examId  Exam ID
     * @param gradeId Class ID
     * @return Query result
     */
    List<ExportScoreVO> selectScores(Integer examId, Integer gradeId);

    /**
     * Get users who have not taken the exam based on the exam ID.
     *
     * @param page   Page information
     * @param examId Exam ID
     * @return Query result
     */
    IPage<UncorrectedUserVO> uncorrectedUser(IPage<UncorrectedUserVO> page, Integer examId,String realName);
}
