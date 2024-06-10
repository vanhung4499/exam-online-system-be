package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.exception.AppException;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.ExamConverter;
import com.hnv99.exam.converter.ExamQuAnswerConverter;
import com.hnv99.exam.mapper.*;
import com.hnv99.exam.model.entity.*;
import com.hnv99.exam.model.form.exam.ExamAddForm;
import com.hnv99.exam.model.form.exam.ExamUpdateForm;
import com.hnv99.exam.model.form.examquanswer.ExamQuAnswerAddForm;
import com.hnv99.exam.model.vo.exam.*;
import com.hnv99.exam.service.ExamQuAnswerService;
import com.hnv99.exam.service.ExamService;
import com.hnv99.exam.service.OptionService;
import com.hnv99.exam.service.QuestionService;
import com.hnv99.exam.util.ClassTokenGenerator;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExamServiceImpl extends ServiceImpl<ExamMapper, Exam> implements ExamService {

    @Resource
    private ExamMapper examMapper;
    @Resource
    private ExamConverter examConverter;
    @Resource
    private ExamQuAnswerService examQuAnswerService;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private QuestionService questionService;
    @Resource
    private ExamGradeMapper examGradeMapper;
    @Resource
    private ExamRepoMapper examRepoMapper;
    @Resource
    private ExamQuestionMapper examQuestionMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private OptionService optionService;
    @Resource
    private ExamQuAnswerMapper examQuAnswerMapper;
    @Resource
    private UserExamsScoreMapper userExamsScoreMapper;
    @Resource
    private UserBookMapper userBookMapper;
    @Resource
    private ExamQuAnswerConverter examQuAnswerConverter;
    @Resource
    private UserMapper userMapper;
    @Resource
    private CertificateUserMapper certificateUserMapper;

    @Override
    @Transactional
    public Result<String> createExam(ExamAddForm examAddForm) {
        // Convert relevant entities related to the exam to Exam entity
        Exam exam = examConverter.formToEntity(examAddForm);

        // Calculate the total score of the exam
        int grossScore = examAddForm.getRadioCount() * examAddForm.getRadioScore()
                + examAddForm.getMultiCount() * examAddForm.getMultiScore()
                + examAddForm.getJudgeCount() * examAddForm.getJudgeScore()
                + examAddForm.getSaqCount() * examAddForm.getSaqScore();
        exam.setGrossScore(grossScore);

        // Add exam information to the exam table
        int examRows = examMapper.insert(exam);
        if (examRows == 0) {
            return Result.failed("Failed to create exam");
        }

        // Add exam grades
        String gradeIdsStr = examAddForm.getGradeIds();
        List<Integer> gradeIds = Arrays.stream(gradeIdsStr.split(","))
                .map(Integer::parseInt)
                .toList();
        Integer gradeRows = examGradeMapper.addExamGrade(exam.getId(), gradeIds);
        if (gradeRows == 0) {
            return Result.failed("Failed to create exam");
        }

        // Set the exam's question bank
        Integer repoId = examAddForm.getRepoId();
        ExamRepo examRepo = new ExamRepo();
        examRepo.setExamId(exam.getId());
        examRepo.setRepoId(repoId);
        int examRepoRows = examRepoMapper.insert(examRepo);
        if (examRepoRows == 0) {
            return Result.failed("Failed to create exam");
        }

        // Start selecting questions
        Map<Integer, Integer> quTypeToCount = new HashMap<>();
        quTypeToCount.put(1, exam.getRadioCount());
        quTypeToCount.put(2, exam.getMultiCount());
        quTypeToCount.put(3, exam.getJudgeCount());
        quTypeToCount.put(4, exam.getSaqCount());

        Map<Integer, Integer> quTypeToScore = new HashMap<>();
        quTypeToScore.put(1, exam.getRadioScore());
        quTypeToScore.put(2, exam.getMultiScore());
        quTypeToScore.put(3, exam.getJudgeScore());
        quTypeToScore.put(4, exam.getSaqScore());

        int sortCounter = 0;

        for (Map.Entry<Integer, Integer> entry : quTypeToCount.entrySet()) {
            Map<Integer, Integer> questionSortMap = new HashMap<>();
            Integer quType = entry.getKey();
            Integer count = entry.getValue();
            Integer examId = exam.getId();
            Integer quScore = quTypeToScore.get(quType);

            LambdaQueryWrapper<Question> typeQueryWrapper = new LambdaQueryWrapper<>();
            typeQueryWrapper.select(Question::getId)
                    .eq(Question::getQuType, quType)
                    .eq(Question::getIsDeleted, 0)
                    .eq(Question::getRepoId, examAddForm.getRepoId());
            List<Question> questionsByType = questionMapper.selectList(typeQueryWrapper);

            if (questionsByType.size() < count) {
                throw new AppException("The number of questions of type " + quType + " in the question bank is insufficient (" + count + ")");
            }

            List<Integer> typeQuestionIds = questionsByType.stream().map(Question::getId).collect(Collectors.toList());
            Collections.shuffle(typeQuestionIds);
            List<Integer> sampledIds = typeQuestionIds.subList(0, count);

            if (!sampledIds.isEmpty()) {
                for (Integer qId : sampledIds) {
                    questionSortMap.put(qId, sortCounter);
                    sortCounter++;
                }

                List<Map<String, Object>> questionDetails = new ArrayList<>();
                for (Map.Entry<Integer, Integer> sortEntry : questionSortMap.entrySet()) {
                    Map<String, Object> detail = new HashMap<>();
                    detail.put("questionId", sortEntry.getKey());
                    detail.put("sort", sortEntry.getValue());
                    questionDetails.add(detail);
                }

                int examQueRows = examQuestionMapper.insertQuestion(examId, quType, quScore, questionDetails);
                if (examQueRows == 0) {
                    return Result.failed("Failed to create exam");
                }
            }
        }

        return Result.success("Exam created successfully");
    }


    @Override
    @Transactional
    public Result<String> updateExam(ExamUpdateForm examUpdateForm, Integer examId) {
        // Modify the exam table record
        Exam examTemp = this.getById(examId);
        int grossScore = examTemp.getRadioCount() * examUpdateForm.getRadioScore()
                + examTemp.getMultiCount() * examUpdateForm.getMultiScore()
                + examTemp.getJudgeCount() * examUpdateForm.getJudgeScore()
                + examTemp.getSaqCount() * examUpdateForm.getSaqScore();
        LambdaUpdateWrapper<Exam> examLambdaUpdate = new LambdaUpdateWrapper<>();
        examLambdaUpdate.eq(Exam::getId, examId)
                .eq(Exam::getUserId, SecurityUtil.getUserId());
        Exam exam = examConverter.formToEntity(examUpdateForm);
        exam.setGrossScore(grossScore);
        int examRows = examMapper.update(exam, examLambdaUpdate);
        if (examRows == 0) {
            throw new AppException("Modification failed");
        }
        return Result.success("Modification successful");
    }

    @Override
    @Transactional
    public Result<String> deleteExam(String ids) {
        // Delete exam table records
        List<Integer> examIds = Arrays.stream(ids.split(","))
                .map(Integer::parseInt)
                .toList();
        int examRows = examMapper.deleteExams(examIds);
        if (examRows == 0) {
            throw new AppException("Deletion failed, unable to delete records from the exam table");
        }
//        // Delete exam class records
//        int examGradeRows = examMapper.deleteExamGrades(examIds);
//        if (examGradeRows == 0) {
//            throw new AppException("Deletion failed, failed to delete exam grade table");
//        }
//        // Delete exam question bank records
//        int examRepoRows = examMapper.deleteExamRepos(examIds);
//        if (examRepoRows == 0) {
//            throw new AppException("Deletion failed, failed to delete exam repository table");
//        }
//        // Delete exam question records
//        int examQueRows = examMapper.deleteExamQuestions(examIds);
//        if (examQueRows == 0) {
//            throw new AppException("Deletion failed, failed to delete exam question table");
//        }
        return Result.success("Deletion successful");
    }

    @Override
    public Result<IPage<ExamVO>> getPagingExam(Integer pageNum, Integer pageSize, String title) {
        // Create a Page object
        Page<Exam> page = new Page<>(pageNum, pageSize);
        // Start querying
        LambdaQueryWrapper<Exam> examQuery = new LambdaQueryWrapper<>();
        examQuery.like(StringUtils.isNotBlank(title), Exam::getTitle, title)
                .eq(Exam::getIsDeleted,0)
                .eq(Exam::getUserId, SecurityUtil.getUserId());
        Page<Exam> examPage = examMapper.selectPage(page, examQuery);
        // Entity conversion
        Page<ExamVO> examVOPage = examConverter.pageEntityToVo(examPage);
        return Result.success("Query successful", examVOPage);
    }

    @Override
    public Result<ExamQuestionListVO> getQuestionList(Integer examId) {
        // Check if the exam is ongoing
        if (!isUserTakingExam(examId)) {
            return Result.failed("Exam is in progress");
        }
        ExamQuestionListVO examQuestionListVO = new ExamQuestionListVO();
        // Set the countdown
        Exam byId = this.getById(examId);
        examQuestionListVO.setExamDuration(byId.getExamDuration());
        LambdaQueryWrapper<UserExamsScore> userExamsScoreLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userExamsScoreLambdaQueryWrapper.eq(UserExamsScore::getUserId, SecurityUtil.getUserId())
                .eq(UserExamsScore::getExamId, examId)
                .eq(UserExamsScore::getState, 0);
        UserExamsScore userExamsScore = userExamsScoreMapper.selectOne(userExamsScoreLambdaQueryWrapper);
        Calendar cl = Calendar.getInstance();
        LocalDateTime createTime = userExamsScore.getCreateTime();
        if (createTime != null) {
            Date date = Date.from(createTime.atZone(ZoneId.systemDefault()).toInstant());
            cl.setTime(date);
        } else {
            return Result.failed("Error");
        }
        cl.add(Calendar.MINUTE, byId.getExamDuration());
        examQuestionListVO.setLeftSeconds((cl.getTimeInMillis() - System.currentTimeMillis()) / 1000);
        // Add lists of questions of different types
        for (int i = 1; i <= 4; i++) {
            // Query the exam question table based on the exam id
            LambdaQueryWrapper<ExamQuestion> examQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
            examQuestionLambdaQueryWrapper.eq(ExamQuestion::getExamId, examId)
                    .eq(ExamQuestion::getType, i)
                    .orderByAsc(ExamQuestion::getSort);
            List<ExamQuestion> examQuestionList = examQuestionMapper.selectList(examQuestionLambdaQueryWrapper);
            List<ExamQuestionVO> examQuestionVOS = examConverter.examQuestionListEntityToVO(examQuestionList);
            for (ExamQuestionVO temp : examQuestionVOS) {
                LambdaQueryWrapper<ExamQuAnswer> examQuAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
                examQuAnswerLambdaQueryWrapper.eq(ExamQuAnswer::getQuestionId, temp.getQuestionId())
                        .eq(ExamQuAnswer::getExamId, examId)
                        .eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId());
                List<ExamQuAnswer> examQuAnswers = examQuAnswerMapper.selectList(examQuAnswerLambdaQueryWrapper);
                if (examQuAnswers.size() > 0) {
                    temp.setCheckout(true);
                } else {
                    temp.setCheckout(false);
                }
            }
            if (examQuestionVOS.isEmpty()) {
                continue;
            }
            switch (i) {
                case 1 -> {
                    examQuestionListVO.setRadioList(examQuestionVOS);
                }
                case 2 -> {
                    examQuestionListVO.setMultiList(examQuestionVOS);
                }
                case 3 -> {
                    examQuestionListVO.setJudgeList(examQuestionVOS);
                }
                case 4 -> {
                    examQuestionListVO.setSaqList(examQuestionVOS);
                }
                default -> {

                }
            }

        }
        return Result.success("Query successful", examQuestionListVO);
    }


    @Override
    public Result<ExamQuDetailVO> getQuestionSingle(Integer examId, Integer questionId) {
        // Check if an exam is in progress
        if (!isUserTakingExam(examId)) {
            return Result.failed("No exam is in progress");
        }
        ExamQuDetailVO examQuDetailVO = new ExamQuDetailVO();
        LambdaQueryWrapper<ExamQuestion> examQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        examQuestionLambdaQueryWrapper.eq(ExamQuestion::getQuestionId, questionId)
                .eq(ExamQuestion::getExamId, examId);
        ExamQuestion examQuestion = examQuestionMapper.selectOne(examQuestionLambdaQueryWrapper);
        examQuDetailVO.setSort(examQuestion.getSort());
        // Question
        Question quById = questionService.getById(questionId);
        // Basic information
        examQuDetailVO.setImage(quById.getImage());
        examQuDetailVO.setContent(quById.getContent());
        examQuDetailVO.setQuType(quById.getQuType());
        // Answer list
        LambdaQueryWrapper<Option> optionLambdaQuery = new LambdaQueryWrapper<>();
        optionLambdaQuery.eq(Option::getQuId, questionId);
        List<Option> list = optionMapper.selectList(optionLambdaQuery);
        List<OptionVO> optionVOS = examConverter.opListEntityToVO(list);
        for (OptionVO temp : optionVOS) {
            LambdaQueryWrapper<ExamQuAnswer> examQuAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
            examQuAnswerLambdaQueryWrapper.eq(ExamQuAnswer::getQuestionId, temp.getQuId())
                    .eq(ExamQuAnswer::getExamId, examId)
                    .eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId());
            List<ExamQuAnswer> examQuAnswers = examQuAnswerMapper.selectList(examQuAnswerLambdaQueryWrapper);
            if (examQuAnswers.size() > 0) {
                for (ExamQuAnswer temp1 : examQuAnswers) {
                    Integer questionType = temp1.getQuestionType();
                    String answerId = temp1.getAnswerId();
                    String answerContent = temp1.getAnswerContent();
                    String idstr = temp.getId().toString();
                    switch (questionType) {
                        case 1, 3 -> {
                            if (answerId.equals(idstr)) {
                                temp.setCheckout(true);
                            } else {
                                temp.setCheckout(false);
                            }
                        }
                        case 2 -> {
                            // Analyze user's answer
                            List<Integer> quIds = Arrays.stream(temp1.getAnswerId().split(","))
                                    .map(Integer::parseInt)
                                    .toList();
                            if (quIds.contains(temp.getId())) {
                                temp.setCheckout(true);
                            } else {
                                temp.setCheckout(false);
                            }
                        }
                        case 4 -> {
                            temp.setContent(answerContent);
                            examQuDetailVO.setAnswerList(optionVOS);
                        }
                        default -> {
                        }
                    }
                }
            }
        }
        if (quById.getQuType() != 4) {
            examQuDetailVO.setAnswerList(optionVOS);
        }
        return Result.success("Successfully retrieved", examQuDetailVO);
    }

    @Override
    public Result<List<ExamQuCollectVO>> getCollect(Integer examId) {
        // Check if an exam is in progress
        if (!isUserTakingExam(examId)) {
            return Result.failed("No exam is in progress");
        }

        List<ExamQuCollectVO> examQuCollectVOS = new ArrayList<>();
        // Query the questions for this exam
        LambdaQueryWrapper<ExamQuestion> examQuestionWrapper = new LambdaQueryWrapper<>();
        examQuestionWrapper.eq(ExamQuestion::getExamId, examId);
        List<ExamQuestion> examQuestions = examQuestionMapper.selectList(examQuestionWrapper);
        List<Integer> quIds = examQuestions.stream()
                .map(ExamQuestion::getQuestionId)
                .collect(Collectors.toList());
        // Query the content of the questions
        List<Question> questions = questionMapper.selectBatchIds(quIds);
        for (Question temp : questions) {
            // Create the return object
            ExamQuCollectVO examQuCollectVO = new ExamQuCollectVO();
            // Set the title
            examQuCollectVO.setTitle(temp.getContent());
            examQuCollectVO.setQuType(temp.getQuType());

            // Query the options of the question
            LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
            optionWrapper.eq(Option::getQuId, temp.getId());
            List<Option> options = optionMapper.selectList(optionWrapper);
            if (temp.getQuType() == 4) {
                examQuCollectVO.setOption(null);
            } else {
                examQuCollectVO.setOption(options);
            }


            // Set whether the answer is correct
            LambdaQueryWrapper<ExamQuAnswer> examQuAnswerWrapper = new LambdaQueryWrapper<>();
            examQuAnswerWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                    .eq(ExamQuAnswer::getExamId, examId)
                    .eq(ExamQuAnswer::getQuestionId, temp.getId());
            ExamQuAnswer examQuAnswer = examQuAnswerMapper.selectOne(examQuAnswerWrapper);
            // If a question has not been answered
            if (examQuAnswer == null) {
                examQuCollectVO.setMyOption(null);
                examQuCollectVOS.add(examQuCollectVO);
                continue;
            }
            switch (temp.getQuType()) {
                case 1 -> {
                    // Set the chosen option
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getId, examQuAnswer.getAnswerId());
                    Option op1 = optionMapper.selectOne(optionLambdaQueryWrapper);
                    examQuCollectVO.setMyOption(Integer.toString(op1.getSort()));

                }
                case 2 -> {
                    // Parse the answer ID into a list
                    String answerId = examQuAnswer.getAnswerId();
                    List<Integer> opIds = Arrays.stream(answerId.split(","))
                            .map(Integer::parseInt)
                            .toList();
                    // Add the option orders
                    List<Integer> sorts = new ArrayList<>();
                    for (Integer opId : opIds) {
                        LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        optionLambdaQueryWrapper.eq(Option::getId, opId);
                        Option option = optionMapper.selectOne(optionLambdaQueryWrapper);
                        sorts.add(option.getSort());
                    }
                    // Set the chosen options, where options are ordered (1 for A, 2 for B, etc.)
                    List<String> shortList = sorts.stream().map(String::valueOf).collect(Collectors.toList());
                    String myOption = String.join(",", shortList);
                    examQuCollectVO.setMyOption(myOption);
                }
                case 3 -> {
                    // Query the chosen option
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getId, examQuAnswer.getAnswerId());
                    Option op1 = optionMapper.selectOne(optionLambdaQueryWrapper);
                    examQuCollectVO.setMyOption(Integer.toString(op1.getSort()));

                }
                case 4 -> {
                    examQuCollectVO.setMyOption(examQuAnswer.getAnswerContent());
                }
                default -> {
                }
            }
            examQuCollectVOS.add(examQuCollectVO);
        }
        return Result.success("Query successful", examQuCollectVOS);
    }


    @Override
    public Result<ExamDetailVO> getDetail(Integer examId) {
        // Query exam details
        Exam exam = this.getById(examId);
        // Convert entity
        ExamDetailVO examDetailVO = examConverter.examToExamDetailVO(exam);
        LambdaQueryWrapper<User> userLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userLambdaQueryWrapper.eq(User::getId, examDetailVO.getUserId());
        User user = userMapper.selectOne(userLambdaQueryWrapper);
        examDetailVO.setUsername(user.getUserName());
        return Result.success("Query successful", examDetailVO);
    }

    @Override
    public Result<Integer> addCheat(Integer examId) {
        // Check if an exam is in progress
        // if(isUserTakingExam(examId)){
        //     return Result.failed("No exam is in progress");
        // }
        LambdaQueryWrapper<UserExamsScore> userExamsScoreLambdaQuery = new LambdaQueryWrapper<>();
        userExamsScoreLambdaQuery.eq(UserExamsScore::getExamId, examId)
                .eq(UserExamsScore::getUserId, SecurityUtil.getUserId());
        UserExamsScore userExamsScore = userExamsScoreMapper.selectOne(userExamsScoreLambdaQuery);
        Exam exam = this.getById(examId);
        // Number of operations, automatically submit the paper
        if (userExamsScore.getCount() >= exam.getMaxCount()) {
            this.handExam(examId);
            return Result.success("The maximum number of screen switches has been exceeded, automatically submitted", 1);
        }
        LambdaUpdateWrapper<UserExamsScore> userExamsScoreLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userExamsScoreLambdaUpdateWrapper.eq(UserExamsScore::getExamId, examId)
                .eq(UserExamsScore::getUserId, SecurityUtil.getUserId())
                .set(UserExamsScore::getCount, userExamsScore.getCount() + 1);
        int insert = userExamsScoreMapper.update(userExamsScoreLambdaUpdateWrapper);
        return Result.success("Please do not switch screens, maximum number of screen switches: " + exam.getMaxCount() + ", current number of screen switches: " + (userExamsScore.getCount() + 1), 0);
    }

    @Override
    public Result<String> addAnswer(ExamQuAnswerAddForm examQuAnswerForm) {
        // Check if an exam is in progress
        // if(isUserTakingExam(examQuAnswerForm.getExamId())){
        //     return Result.failed("No exam is in progress");
        // }
        // Query the question type
        LambdaQueryWrapper<Question> QuWrapper = new LambdaQueryWrapper<>();
        QuWrapper.eq(Question::getId, examQuAnswerForm.getQuId());
        Question qu = questionMapper.selectOne(QuWrapper);
        Integer quType = qu.getQuType();
        // Check if there is a record
        LambdaQueryWrapper<ExamQuAnswer> examQuAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
        examQuAnswerLambdaQueryWrapper.eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                .eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId());
        List<ExamQuAnswer> existingAnswers = examQuAnswerMapper.selectList(examQuAnswerLambdaQueryWrapper);
        if (!existingAnswers.isEmpty()) {
            // Update logic, handle logic merging based on the question type
            return updateAnswerIfExists(examQuAnswerForm, quType);
        } else {
            // Insert logic, also handle based on the question type
            return insertNewAnswer(examQuAnswerForm, quType);
        }
    }

    @Override
    public Result<String> insertNewAnswer(ExamQuAnswerAddForm examQuAnswerForm, Integer quType) {
        // Modify according to the question type
        ExamQuAnswer examQuAnswer = prepareExamQuAnswer(examQuAnswerForm, quType);
        switch (quType) {
            case 1:
                Option byId = optionService.getById(examQuAnswerForm.getAnswer());
                if (byId.getIsRight() == 1) {
                    examQuAnswer.setIsRight(1);
                    examQuAnswerMapper.insert(examQuAnswer);
                    return Result.success("Request successful");
                } else {
                    examQuAnswer.setIsRight(0);
                    examQuAnswerMapper.insert(examQuAnswer);
                    return Result.success("Request successful");
                }
            case 2:
                // Find the correct answer
                LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
                optionWrapper.eq(Option::getIsRight, 1)
                        .eq(Option::getQuId, examQuAnswerForm.getQuId());
                List<Option> examQuAnswers = optionMapper.selectList(optionWrapper);
                // Parse user's answer
                List<Integer> quIds = Arrays.stream(examQuAnswerForm.getAnswer().split(","))
                        .map(Integer::parseInt)
                        .toList();
                // Check if the answer is correct
                for (Option temp : examQuAnswers) {
                    if (!quIds.contains(temp.getId())) {
                        examQuAnswer.setIsRight(0);
                        examQuAnswerMapper.insert(examQuAnswer);
                        return Result.success("Wrong answer");
                    }
                }
                examQuAnswer.setIsRight(1);
                examQuAnswerMapper.insert(examQuAnswer);
                return Result.success("Correct answer");
            case 3:
                Option byId3 = optionService.getById(examQuAnswerForm.getAnswer());
                if (byId3.getIsRight() == 1) {
                    examQuAnswer.setIsRight(1);
                    examQuAnswerMapper.insert(examQuAnswer);
                    return Result.success("Request successful");
                } else {
                    examQuAnswer.setIsRight(0);
                    examQuAnswerMapper.insert(examQuAnswer);
                    return Result.success("Request successful");
                }
            case 4:
                LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                optionLambdaQueryWrapper.eq(Option::getQuId, examQuAnswerForm.getQuId());
                Option option = optionMapper.selectOne(optionLambdaQueryWrapper);
                if (option.getContent().equals(examQuAnswerForm.getAnswer())) {
                    examQuAnswer.setIsRight(1);
                } else {
                    examQuAnswer.setIsRight(0);
                }
                examQuAnswerMapper.insert(examQuAnswer);
                return Result.success("Request successful");
            default:
                return Result.failed("Request error, please contact the administrator");
        }
    }
    @Override
    public Result<String> updateAnswerIfExists(ExamQuAnswerAddForm examQuAnswerForm, Integer quType) {
        // Update based on the question type
        return switch (quType) {
            case 1 -> {
                Option byId = optionService.getById(examQuAnswerForm.getAnswer());
                if (byId == null) {
                    yield Result.failed("The question does not exist in the database. Please contact the administrator.");
                }
                if (byId.getIsRight() == 1) {
                    LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerWrapper = new LambdaUpdateWrapper<>();
                    examQuAnswerWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                            .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                            .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                            .set(ExamQuAnswer::getIsRight, 1)
                            .set(ExamQuAnswer::getAnswerId, examQuAnswerForm.getAnswer());
                    examQuAnswerMapper.update(examQuAnswerWrapper);
                    yield Result.success("Request successful");
                } else {
                    LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    examQuAnswerLambdaUpdateWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                            .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                            .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                            .set(ExamQuAnswer::getIsRight, 0)
                            .set(ExamQuAnswer::getAnswerId, examQuAnswerForm.getAnswer());
                    examQuAnswerMapper.update(examQuAnswerLambdaUpdateWrapper);
                    yield Result.success("Request successful");
                }
            }
            case 2 -> {
                // Find the correct answer
                LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
                optionWrapper.eq(Option::getIsRight, 1)
                        .eq(Option::getQuId, examQuAnswerForm.getQuId());
                List<Option> examQuAnswers = optionMapper.selectList(optionWrapper);
                if (examQuAnswers.isEmpty()) {
                    yield Result.failed("The correct answer option for this question does not exist.");
                }
                // Parse user's answers
                List<Integer> quIds = Arrays.stream(examQuAnswerForm.getAnswer().split(","))
                        .map(Integer::parseInt)
                        .toList();
                // Check if the answers are correct
                for (Option temp : examQuAnswers) {
                    if (!quIds.contains(temp.getId())) {
                        LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerWrapper = new LambdaUpdateWrapper<>();
                        examQuAnswerWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                                .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                                .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                                .set(ExamQuAnswer::getIsRight, 0)
                                .set(ExamQuAnswer::getAnswerId, examQuAnswerForm.getAnswer());
                        examQuAnswerMapper.update(examQuAnswerWrapper);
                        yield Result.success("Answer incorrect");
                    }
                }
                LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerWrapper = new LambdaUpdateWrapper<>();
                examQuAnswerWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                        .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                        .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                        .set(ExamQuAnswer::getIsRight, 1)
                        .set(ExamQuAnswer::getAnswerId, examQuAnswerForm.getAnswer());
                examQuAnswerMapper.update(examQuAnswerWrapper);
                yield Result.success("Answer correct");
            }
            case 3 -> {
                Option byId = optionService.getById(examQuAnswerForm.getAnswer());
                if (byId == null) {
                    yield Result.failed("The question does not exist in the database. Please contact the administrator.");
                }
                if (byId.getIsRight() == 1) {
                    LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerWrapper = new LambdaUpdateWrapper<>();
                    examQuAnswerWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                            .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                            .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                            .set(ExamQuAnswer::getIsRight, 1)
                            .set(ExamQuAnswer::getAnswerId, examQuAnswerForm.getAnswer());
                    examQuAnswerMapper.update(examQuAnswerWrapper);
                    yield Result.success("Request successful");
                } else {
                    LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                    examQuAnswerLambdaUpdateWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                            .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                            .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                            .set(ExamQuAnswer::getIsRight, 0)
                            .set(ExamQuAnswer::getAnswerId, examQuAnswerForm.getAnswer());
                    examQuAnswerMapper.update(examQuAnswerLambdaUpdateWrapper);
                    yield Result.success("Request successful");
                }
            }
            case 4 -> {
                LambdaUpdateWrapper<ExamQuAnswer> examQuAnswerLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
                examQuAnswerLambdaUpdateWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                        .eq(ExamQuAnswer::getExamId, examQuAnswerForm.getExamId())
                        .eq(ExamQuAnswer::getQuestionId, examQuAnswerForm.getQuId())
                        .set(ExamQuAnswer::getAnswerContent, examQuAnswerForm.getAnswer());
                examQuAnswerMapper.update(examQuAnswerLambdaUpdateWrapper);
                yield Result.success("Request successful");
            }
            default -> Result.failed("Invalid request. Please contact the administrator.");
        };
    }


    @Override
    public ExamQuAnswer prepareExamQuAnswer(ExamQuAnswerAddForm form, Integer quType) {
        // Convert form to entity
        ExamQuAnswer examQuAnswer = examQuAnswerConverter.formToEntity(form);
        if (quType == 4) {
            examQuAnswer.setAnswerContent(form.getAnswer());
        } else {
            examQuAnswer.setAnswerId(form.getAnswer());
        }

        examQuAnswer.setUserId(SecurityUtil.getUserId());
        examQuAnswer.setQuestionType(quType);
        return examQuAnswer;
    }

    @Override
    public boolean isUserTakingExam(Integer examId) {
        // Check if the user is currently taking the exam
        LambdaQueryWrapper<UserExamsScore> userExamsScoreLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userExamsScoreLambdaQueryWrapper.eq(UserExamsScore::getUserId, SecurityUtil.getUserId())
                .eq(UserExamsScore::getExamId, examId)
                .eq(UserExamsScore::getState, 0);
        List<UserExamsScore> userExamsScores = userExamsScoreMapper.selectList(userExamsScoreLambdaQueryWrapper);
        return !userExamsScores.isEmpty();
    }

    @Override
    public Result<IPage<ExamGradeListVO>> getGradeExamList(Integer pageNum, Integer pageSize, String title) {
        IPage<ExamGradeListVO> examPage = new Page<>(pageNum, pageSize);
        // Find exam IDs based on class
        if ("role_student".equals(SecurityUtil.getRole())) {
            examPage = examGradeMapper.selectClassExam(examPage, SecurityUtil.getUserId(), title);
        } else if ("role_admin".equals(SecurityUtil.getRole())) {
            examPage = examGradeMapper.selectAdminClassExam(examPage, title);
        }
        // Find exams based on exam IDs
        return Result.success("Query successful", examPage);
    }

    @Override
    @Transactional
    public Result<ExamQuDetailVO> handExam(Integer examId) {
        // Check if the user is currently taking the exam
        if (!isUserTakingExam(examId)) {
            return Result.failed("No exam is in progress");
        }
        // Get the current time
        LocalDateTime nowTime = LocalDateTime.now();
        // Retrieve exam record from the database
        Exam examOne = this.getById(examId);
        // Check if submitting the exam is overdue
        LocalDateTime endTime = examOne.getEndTime();
        if (endTime.isBefore(nowTime)) {
            return Result.failed("Submission failed, the submission time has passed");
        }
        // Set the exam status
        UserExamsScore userExamsScore = new UserExamsScore();
        userExamsScore.setUserScore(0);
        userExamsScore.setState(1);

        // Retrieve user's answer records
        LambdaQueryWrapper<ExamQuAnswer> examQuAnswerLambdaQuery = new LambdaQueryWrapper<>();
        examQuAnswerLambdaQuery.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                .eq(ExamQuAnswer::getExamId, examId);
        List<ExamQuAnswer> examQuAnswer = examQuAnswerMapper.selectList(examQuAnswerLambdaQuery);
        // Objective score
        List<UserBook> userBookArrayList = new ArrayList<>();
        for (ExamQuAnswer temp : examQuAnswer) {
            if (temp.getIsRight() == 1) {
                if (temp.getQuestionType() == 1) {
                    userExamsScore.setUserScore(userExamsScore.getUserScore() + examOne.getRadioScore());
                } else if (temp.getQuestionType() == 2) {
                    userExamsScore.setUserScore(userExamsScore.getUserScore() + examOne.getMultiScore());
                } else if (temp.getQuestionType() == 3) {
                    userExamsScore.setUserScore(userExamsScore.getUserScore() + examOne.getJudgeScore());
                }
            } else if (temp.getIsRight() == 0) {
                UserBook userBook = new UserBook();
                userBook.setExamId(examId);
                userBook.setUserId(SecurityUtil.getUserId());
                userBook.setQuId(temp.getQuestionId());
                userBook.setCreateTime(nowTime);
                userBookArrayList.add(userBook);
            }
        }
        if (!userBookArrayList.isEmpty()) {
            // Add wrongly answered questions to the user's book
            userBookMapper.addUserBookList(userBookArrayList);
        }
        // Set user's time used and submit the exam
        userExamsScore.setLimitTime(nowTime);
        // Start time
        LambdaQueryWrapper<UserExamsScore> userExamsScoreLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userExamsScoreLambdaQueryWrapper.eq(UserExamsScore::getUserId, SecurityUtil.getUserId())
                .eq(UserExamsScore::getExamId, examId);
        UserExamsScore userExamsScore1 = userExamsScoreMapper.selectOne(userExamsScoreLambdaQueryWrapper);
        LocalDateTime createTime = userExamsScore1.getCreateTime();
        long secondsDifference = Duration.between(createTime, nowTime).getSeconds();
        int differenceAsInteger = (int) secondsDifference;
        // Check if it's within the range of Integer
        // if (secondsDifference <= Integer.MAX_VALUE && secondsDifference >= Integer.MIN_VALUE)
        userExamsScore.setUserTime(differenceAsInteger);
        // Add total score and status
        LambdaUpdateWrapper<UserExamsScore> userExamsScoreLambdaUpdate = new LambdaUpdateWrapper<>();
        userExamsScoreLambdaUpdate.eq(UserExamsScore::getUserId, SecurityUtil.getUserId())
                .eq(UserExamsScore::getExamId, examId);
        userExamsScoreMapper.update(userExamsScore, userExamsScoreLambdaUpdate);
        // Check if there are subjective questions
        if (examOne.getSaqCount() != 0) {
            LambdaUpdateWrapper<UserExamsScore> userExamsScoreLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
            userExamsScoreLambdaUpdateWrapper.set(UserExamsScore::getWhetherMark, 0)
                    .eq(UserExamsScore::getExamId, examId)
                    .eq(UserExamsScore::getUserId, SecurityUtil.getUserId());
            userExamsScoreMapper.update(userExamsScoreLambdaUpdateWrapper);
            return Result.success("Submitted successfully, pending teacher review");
        }
        if (userExamsScore.getUserScore() >= examOne.getPassedScore()) {
            CertificateUser certificateUser = new CertificateUser();
            certificateUser.setCertificateId(examOne.getCertificateId());
            certificateUser.setUserId(SecurityUtil.getUserId());
            certificateUser.setExamId(examId);
            certificateUser.setCode(ClassTokenGenerator.generateClassToken(18));
            certificateUserMapper.insert(certificateUser);
        }
        // Check if there are answers to subjective questions
        Exam byId = this.getById(examId);
        if (byId.getSaqCount() > 0) {
            LambdaQueryWrapper<ExamQuAnswer> examQuAnswerLambdaQueryWrapper = new LambdaQueryWrapper<>();
            examQuAnswerLambdaQueryWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                    .eq(ExamQuAnswer::getExamId, examId)
                    .eq(ExamQuAnswer::getQuestionType, 4);
            List<ExamQuAnswer> examQuAnswers = examQuAnswerMapper.selectList(examQuAnswerLambdaQueryWrapper);
            if (examQuAnswers.isEmpty()) {
                LambdaQueryWrapper<ExamQuestion> examQuestionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                examQuestionLambdaQueryWrapper.eq(ExamQuestion::getExamId, examId)
                        .eq(ExamQuestion::getType, 4);
                List<ExamQuestion> examQuestions = examQuestionMapper.selectList(examQuestionLambdaQueryWrapper);
                examQuestions.forEach(temp -> {
                    ExamQuAnswer examQuAnswer1 = new ExamQuAnswer();
                    examQuAnswer1.setExamId(examId);
                    examQuAnswer1.setUserId(SecurityUtil.getUserId());
                    examQuAnswer1.setQuestionId(temp.getQuestionId());
                    examQuAnswer1.setQuestionType(temp.getType());
                    examQuAnswer1.setIsRight(-1);
                    examQuAnswerMapper.insert(examQuAnswer1);
                });
            }

        }

        LambdaUpdateWrapper<UserExamsScore> userExamsScoreLambdaUpdateWrapper = new LambdaUpdateWrapper<>();
        userExamsScoreLambdaUpdateWrapper.set(UserExamsScore::getWhetherMark, -1)
                .eq(UserExamsScore::getExamId, examId)
                .eq(UserExamsScore::getUserId, SecurityUtil.getUserId());
        userExamsScoreMapper.update(userExamsScoreLambdaUpdateWrapper);
        return Result.success("Exam submitted successfully");
    }

    @Override
    public Result<String> startExam(Integer examId) {

        // Check if the user is currently taking an exam

        if (isUserTakingExam(examId)) {
            return Result.failed("An exam is already in progress");
        }
        LambdaQueryWrapper<UserExamsScore> userExamsScoreLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userExamsScoreLambdaQueryWrapper.eq(UserExamsScore::getUserId, SecurityUtil.getUserId())
                .eq(UserExamsScore::getExamId, examId);
        List<UserExamsScore> userExamsScores = userExamsScoreMapper.selectList(userExamsScoreLambdaQueryWrapper);
        if (!userExamsScores.isEmpty()) {
            return Result.failed("You have already taken this exam and cannot take it again");
        }
        Exam exam = this.getById(examId);
        // Add user's exam record
        UserExamsScore userExamsScore = new UserExamsScore();
        userExamsScore.setExamId(examId);
        userExamsScore.setTotalTime(exam.getExamDuration());
        userExamsScore.setState(0);
        int rows = userExamsScoreMapper.insert(userExamsScore);
        if (rows == 0) {
            return Result.failed("Failed to start exam");
        }
        return Result.success("Exam started");
    }

}
