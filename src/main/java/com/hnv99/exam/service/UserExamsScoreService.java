package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.UserExamsScore;
import com.hnv99.exam.model.vo.score.GradeScoreVO;
import com.hnv99.exam.model.vo.score.UserScoreVO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletResponse;

public interface UserExamsScoreService extends IService<UserExamsScore> {

    /**
     * Get score information with pagination
     *
     * @param pageNum Page number
     * @param pageSize Number of records per page
     * @param gradeId Grade ID
     * @param examId Exam ID
     * @param realName Real name
     * @return Query result set
     */
    Result<IPage<UserScoreVO>> pagingScore(Integer pageNum, Integer pageSize, Integer gradeId, Integer examId, String realName);

    /**
     * Export scores
     *
     * @param response Response object
     * @param examId Exam ID
     * @param gradeId Grade ID
     */
    void exportScores(HttpServletResponse response, Integer examId, Integer gradeId);

    /**
     * Analyze exam situation based on grade
     *
     * @param pageNum Page number
     * @param pageSize Number of records per page
     * @param examTitle Exam title
     * @return Response result
     */
    Result<IPage<GradeScoreVO>> getExamScoreInfo(Integer pageNum, Integer pageSize, String examTitle, Integer gradeId);
}
