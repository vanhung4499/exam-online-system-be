package com.hnv99.exam.controller;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.vo.score.GradeScoreVO;
import com.hnv99.exam.model.vo.score.QuestionAnalyseVO;
import com.hnv99.exam.model.vo.score.UserScoreVO;
import com.hnv99.exam.service.ExamQuAnswerService;
import com.hnv99.exam.service.StatService;
import com.hnv99.exam.service.UserExamsScoreService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/score")
public class ScoreController {

    @Resource
    private StatService statService;
    @Resource
    private UserExamsScoreService userExamsScoreService;
    @Resource
    private ExamQuAnswerService examQuAnswerService;

    /**
     * Pagination to obtain score information
     * @param pageNum Page number
     * @param pageSize Records per page
     * @param gradeId Class ID
     * @param examId Exam ID
     * @param realName Real name
     * @return Response result
     */
    @GetMapping("/paging")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<UserScoreVO>> pagingScore(@RequestParam(value = "pageNum", required = false, defaultValue = "1") Integer pageNum,
                                                  @RequestParam(value = "pageSize", required = false, defaultValue = "10") Integer pageSize,
                                                  @RequestParam(value = "gradeId") Integer gradeId,
                                                  @RequestParam(value = "examId") Integer examId,
                                                  @RequestParam(value = "realName", required = false) String realName) {
        return userExamsScoreService.pagingScore(pageNum, pageSize, gradeId, examId, realName);
    }


    /**
     * Get the answer situation of a question in a certain exam
     * @param examId Exam id
     * @param questionId Question id
     * @return Response result
     */
    @GetMapping("/question/{examId}/{questionId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<QuestionAnalyseVO> questionAnalyse(@PathVariable("examId") Integer examId,
                                                     @PathVariable("questionId") Integer questionId) {
        return examQuAnswerService.questionAnalyse(examId, questionId);
    }


    /**
     * Analyze the exam situation by class
     * @param pageNum Page number
     * @param pageSize Records per page
     * @param examTitle Exam title
     * @return Response result
     */
    @GetMapping("/getExamScore")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public Result<IPage<GradeScoreVO>> getExamScoreInfo(
            @RequestParam(value = "pageNum",required = false,defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize",required = false,defaultValue = "10") Integer pageSize,
            @RequestParam(value = "examTitle",required = false) String examTitle,
            @RequestParam(value = "gradeId" ,required = false) Integer gradeId){
        return userExamsScoreService.getExamScoreInfo(pageNum,pageSize,examTitle,gradeId);
    }

    /**
     * Export scores
     * @param response Response object
     * @param examId Exam id
     * @param gradeId Class id
     */
    @GetMapping("/export/{examId}/{gradeId}")
    @PreAuthorize("hasAnyAuthority('role_teacher','role_admin')")
    public void scoreExport(HttpServletResponse response,@PathVariable("examId") Integer examId, @PathVariable("gradeId") Integer gradeId) {
        userExamsScoreService.exportScores(response,examId,gradeId);
    }

}
