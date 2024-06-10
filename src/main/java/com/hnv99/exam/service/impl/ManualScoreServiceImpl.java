package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.mapper.*;
import com.hnv99.exam.model.entity.*;
import com.hnv99.exam.model.form.answer.CorrectAnswerForm;
import com.hnv99.exam.model.vo.answer.AnswerExamVO;
import com.hnv99.exam.model.vo.answer.UncorrectedUserVO;
import com.hnv99.exam.model.vo.answer.UserAnswerDetailVO;
import com.hnv99.exam.service.ManualScoreService;
import com.hnv99.exam.util.ClassTokenGenerator;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;


@Service
public class ManualScoreServiceImpl extends ServiceImpl<ManualScoreMapper, ManualScore> implements ManualScoreService {

    @Resource
    private ExamMapper examMapper;
    @Resource
    private ExamGradeMapper examGradeMapper;
    @Resource
    private UserExamsScoreMapper userExamsScoreMapper;
    @Resource
    private ExamQuAnswerMapper examQuAnswerMapper;
    @Resource
    private ManualScoreMapper manualScoreMapper;
    @Resource
    private CertificateUserMapper certificateUserMapper;


    @Override
    public Result<List<UserAnswerDetailVO>> getDetail(Integer userId, Integer examId) {
        List<UserAnswerDetailVO> list = examQuAnswerMapper.selectUserAnswer(userId, examId);
        return Result.success(null, list);
    }

    @Override
    @Transactional
    public Result<String> correct(List<CorrectAnswerForm> correctAnswerFroms) {
        List<ManualScore> list = new ArrayList<>(correctAnswerFroms.size());
        AtomicInteger manualTotalScore = new AtomicInteger();
        correctAnswerFroms.forEach(correctAnswerFrom -> {
            // Get the ID of the user's answer information
            LambdaQueryWrapper<ExamQuAnswer> wrapper = new LambdaQueryWrapper<ExamQuAnswer>()
                    .select(ExamQuAnswer::getId)
                    .eq(ExamQuAnswer::getExamId, correctAnswerFrom.getExamId())
                    .eq(ExamQuAnswer::getUserId, correctAnswerFrom.getUserId())
                    .eq(ExamQuAnswer::getQuestionId, correctAnswerFrom.getQuestionId());

            ManualScore manualScore = new ManualScore();
            manualScore.setExamQuAnswerId(examQuAnswerMapper.selectOne(wrapper).getId());
            manualScore.setScore(correctAnswerFrom.getScore());
            list.add(manualScore);
            manualTotalScore.addAndGet(correctAnswerFrom.getScore());
        });
        manualScoreMapper.insertList(list);

        // Update user's exam record as marked and add the score of essay questions
        CorrectAnswerForm correctAnswerFrom = correctAnswerFroms.get(0);
        LambdaUpdateWrapper<UserExamsScore> userExamsScoreLambdaUpdateWrapper = new LambdaUpdateWrapper<UserExamsScore>()
                .eq(UserExamsScore::getExamId, correctAnswerFrom.getExamId())
                .eq(UserExamsScore::getUserId, correctAnswerFrom.getUserId())
                .set(UserExamsScore::getWhetherMark, 1)
                .setSql("user_score = user_score + " + manualTotalScore.get());
        userExamsScoreMapper.update(userExamsScoreLambdaUpdateWrapper);

        // Award the corresponding certificate to the user based on whether the exam has a certificate
        // Check if the exam has a certificate
        LambdaQueryWrapper<Exam> examWrapper = new LambdaQueryWrapper<Exam>()
                .select(Exam::getId, Exam::getCertificateId, Exam::getPassedScore)
                .eq(Exam::getId, correctAnswerFrom.getExamId());
        Exam exam = examMapper.selectOne(examWrapper);
        if (exam.getCertificateId() != null && exam.getCertificateId() > 0) {
            // There is a certificate, get the user's score
            LambdaQueryWrapper<UserExamsScore> examsScoreWrapper = new LambdaQueryWrapper<UserExamsScore>()
                    .select(UserExamsScore::getId, UserExamsScore::getUserScore)
                    .eq(UserExamsScore::getExamId, correctAnswerFrom.getExamId())
                    .eq(UserExamsScore::getUserId, correctAnswerFrom.getUserId());
            UserExamsScore userExamsScore = userExamsScoreMapper.selectOne(examsScoreWrapper);
            if (userExamsScore.getUserScore() >= exam.getPassedScore()) {
                // The score is qualified, issue the certificate
                CertificateUser certificateUser = new CertificateUser();
                certificateUser.setUserId(correctAnswerFrom.getUserId());
                certificateUser.setExamId(correctAnswerFrom.getExamId());
                certificateUser.setCode(ClassTokenGenerator.generateClassToken(18));
                certificateUser.setCertificateId(exam.getCertificateId());
                certificateUserMapper.insert(certificateUser);
            }
        }
        return Result.success("Marking successful");
    }


    @Override
    public Result<IPage<AnswerExamVO>> examPage(Integer pageNum, Integer pageSize,String examName) {

        Page<AnswerExamVO> page = new Page<>(pageNum, pageSize);
        // Get exams created by the current user
        List<AnswerExamVO> list = examMapper.selectMarkedList(page, SecurityUtil.getUserId(), SecurityUtil.getRole(),examName).getRecords();

        // Fetch relevant information
        list.forEach(answerExamVO -> {
            // Number of participants required for the exam
            answerExamVO.setClassSize(examGradeMapper.selectClassSize(answerExamVO.getExamId()));
            // Actual number of participants
            LambdaQueryWrapper<UserExamsScore> numberWrapper = new LambdaQueryWrapper<UserExamsScore>()
                    .eq(UserExamsScore::getExamId, answerExamVO.getExamId());
            answerExamVO.setNumberOfApplicants(userExamsScoreMapper.selectCount(numberWrapper).intValue());
            // Number of papers already corrected
            LambdaQueryWrapper<UserExamsScore> correctedWrapper = new LambdaQueryWrapper<UserExamsScore>()
                    .eq(UserExamsScore::getWhetherMark, 1)
                    .eq(UserExamsScore::getExamId, answerExamVO.getExamId());
            answerExamVO.setCorrectedPaper(userExamsScoreMapper.selectCount(correctedWrapper).intValue());
        });
        // Remove papers that do not require marking
        page.setRecords(list.stream()
                .filter(answerExamVO -> answerExamVO.getNeededMark() == 1)
                .toList());

        return Result.success(null, page);
    }


    @Override

    public Result<IPage<UncorrectedUserVO>> stuExamPage(Integer pageNum, Integer pageSize, Integer examId,String realName) {
        IPage<UncorrectedUserVO> page = new Page<>(pageNum, pageSize);
        page = userExamsScoreMapper.uncorrectedUser(page, examId,realName);
        return Result.success(null, page);
    }
}
