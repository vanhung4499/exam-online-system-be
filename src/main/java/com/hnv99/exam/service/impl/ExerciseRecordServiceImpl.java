package com.hnv99.exam.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.ExerciseConverter;
import com.hnv99.exam.converter.RecordConverter;
import com.hnv99.exam.mapper.*;
import com.hnv99.exam.model.entity.*;
import com.hnv99.exam.model.form.ExerciseFillAnswerFrom;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.model.vo.exercise.AnswerInfoVO;
import com.hnv99.exam.model.vo.exercise.QuestionSheetVO;
import com.hnv99.exam.model.vo.record.ExamRecordDetailVO;
import com.hnv99.exam.model.vo.record.ExamRecordVO;
import com.hnv99.exam.model.vo.record.ExerciseRecordDetailVO;
import com.hnv99.exam.model.vo.record.ExerciseRecordVO;
import com.hnv99.exam.service.ExerciseRecordService;
import com.hnv99.exam.service.OptionService;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExerciseRecordServiceImpl extends ServiceImpl<ExerciseRecordMapper, ExerciseRecord>
        implements ExerciseRecordService {

    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private ExamMapper examMapper;
    @Resource
    private RecordConverter recordConverter;
    @Resource
    private ExamQuestionMapper examQuestionMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private ExamQuAnswerMapper examQuAnswerMapper;
    @Resource
    private OptionService optionService;
    @Resource
    private UserExerciseRecordMapper userExerciseRecordMapper;
    @Resource
    private RepoMapper repoMapper;
    @Resource
    private ExerciseConverter exerciseConverter;
    @Resource
    private ExerciseRecordMapper exerciseRecordMapper;


    @Override
    public Result<List<QuestionSheetVO>> getQuestionSheet(Integer repoId, Integer quType) {
        List<QuestionSheetVO> list = questionMapper.selectQuestionSheet(repoId, quType, SecurityUtil.getUserId());
//
//        LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<Question>()
//                .select(Question::getId, Question::getQuType, Question::getRepoId)
//                .eq(Question::getRepoId, repoId).eq(quType != null && quType != 0, Question::getQuType, quType);
//
//        List<QuestionSheetVO> list = questionConverter.listEntityToVO(questionMapper.selectList(wrapper));
//
//        list.forEach(questionSheetVO -> {
//            LambdaQueryWrapper<ExerciseRecord> exerciseRecordLambdaQueryWrapper = new LambdaQueryWrapper<ExerciseRecord>()
//                    .eq(ExerciseRecord::getRepoId, questionSheetVO.getRepoId())
//                    .eq(ExerciseRecord::getQuestionId, questionSheetVO.getQuId())
//                    .eq(ExerciseRecord::getUserId, SecurityUtil.getUserId());
//
//            ExerciseRecord exerciseRecord = exerciseRecordMapper.selectOne(exerciseRecordLambdaQueryWrapper);
//
//            questionSheetVO.setExercised(Optional.ofNullable(exerciseRecord).isEmpty() ? 0 : 1);
//        });

        return Result.success(null, list);
    }

    @Override
    public Result<IPage<ExamRecordVO>> getExamRecordPage(Integer pageNum, Integer pageSize,String examName) {
        // Create a page object
        Page<ExamRecordVO> examPage = new Page<>(pageNum, pageSize);
        // Query the exams that the user has taken
        examPage = examMapper.getExamRecordPage(examPage, SecurityUtil.getUserId(),examName);
        return Result.success("Query successful", examPage);
    }

    @Override
    public Result<List<ExamRecordDetailVO>> getExamRecordDetail(Integer examId) {
        // 1. Title 2. Options 3. User's answer 4. Correct answer 5. Whether it's correct 6. Question analysis
        List<ExamRecordDetailVO> examRecordDetailVOS = new ArrayList<>();
        // Query questions for this exam
        LambdaQueryWrapper<ExamQuestion> examQuestionWrapper = new LambdaQueryWrapper<>();
        examQuestionWrapper.eq(ExamQuestion::getExamId, examId);
        List<ExamQuestion> examQuestions = examQuestionMapper.selectList(examQuestionWrapper);
        List<Integer> quIds = examQuestions.stream()
                .map(ExamQuestion::getQuestionId)
                .collect(Collectors.toList());
        // Query question details
        List<Question> questions = questionMapper.selectBatchIds(quIds);
        for (Question temp : questions) {
            // Create a return object
            ExamRecordDetailVO examRecordDetailVO = new ExamRecordDetailVO();
            // Set title
            examRecordDetailVO.setImage(temp.getImage());
            examRecordDetailVO.setTitle(temp.getContent());
            examRecordDetailVO.setQuType(temp.getQuType());
            // Set analysis
            examRecordDetailVO.setAnalyse(temp.getAnalysis());
            // Query question options
            LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
            optionWrapper.eq(Option::getQuId, temp.getId());
            List<Option> options = optionMapper.selectList(optionWrapper);
            if (temp.getQuType() == 4) {
                examRecordDetailVO.setOption(null);
            } else {
                examRecordDetailVO.setOption(options);
            }

            // Query question type
            LambdaQueryWrapper<Question> QuWrapper = new LambdaQueryWrapper<>();
            QuWrapper.eq(Question::getId, temp.getId());
            Question qu = questionMapper.selectOne(QuWrapper);
            Integer quType = qu.getQuType();
            // Set correct answer
            LambdaQueryWrapper<Option> opWrapper = new LambdaQueryWrapper<>();
            opWrapper.eq(Option::getQuId, temp.getId());
            List<Option> opList = optionMapper.selectList(opWrapper);

            if (temp.getQuType() == 4 && opList.size() > 0) {
                examRecordDetailVO.setRightOption(opList.get(0).getContent());
            } else {
                String current = "";
                ArrayList<Integer> strings = new ArrayList<>();
                for (Option temp1 : options) {
                    if (temp1.getIsRight() == 1) {
                        strings.add(temp1.getSort());
                    }
                }
                List<String> stringList = strings.stream().map(String::valueOf).collect(Collectors.toList());
                String result = String.join(",", stringList);

                examRecordDetailVO.setRightOption(result);
            }
            // Set whether it's correct
            LambdaQueryWrapper<ExamQuAnswer> examQuAnswerWrapper = new LambdaQueryWrapper<>();
            examQuAnswerWrapper.eq(ExamQuAnswer::getUserId, SecurityUtil.getUserId())
                    .eq(ExamQuAnswer::getExamId, examId)
                    .eq(ExamQuAnswer::getQuestionId, temp.getId());
            ExamQuAnswer examQuAnswer = examQuAnswerMapper.selectOne(examQuAnswerWrapper);
            // If the question hasn't been answered
            if (examQuAnswer == null) {
                examRecordDetailVO.setMyOption(null);
                examRecordDetailVO.setIsRight(-1);
                examRecordDetailVOS.add(examRecordDetailVO);
                continue;
            }
            switch (quType) {
                case 1 -> {
                    // Set user's answer
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getId, examQuAnswer.getAnswerId());
                    Option op1 = optionMapper.selectOne(optionLambdaQueryWrapper);
                    examRecordDetailVO.setMyOption(Integer.toString(op1.getSort()));
                    // Set whether it's correct
                    Option byId = optionService.getById(examQuAnswer.getAnswerId());
                    if (byId.getIsRight() == 1) {
                        examRecordDetailVO.setIsRight(1);
                    } else {
                        examRecordDetailVO.setIsRight(0);
                    }
                }
                case 2 -> {
                    // Parse answer IDs into a list
                    String answerId = examQuAnswer.getAnswerId();
                    List<Integer> opIds = Arrays.stream(answerId.split(","))
                            .map(Integer::parseInt)
                            .toList();
                    // Add option order
                    List<Integer> sorts = new ArrayList<>();
                    for (Integer opId : opIds) {
                        LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                        optionLambdaQueryWrapper.eq(Option::getId, opId);
                        Option option = optionMapper.selectOne(optionLambdaQueryWrapper);
                        sorts.add(option.getSort());
                    }
                    // Set user's selected options, options are in order, 1 for A, 2 for B...
                    List<String> shortList = sorts.stream().map(String::valueOf).collect(Collectors.toList());
                    String myOption = String.join(",", shortList);
                    examRecordDetailVO.setMyOption(myOption);
                    // Find correct answer
                    LambdaQueryWrapper<Option> optionWrapper1 = new LambdaQueryWrapper<>();
                    optionWrapper1.eq(Option::getIsRight, 1)
                            .eq(Option::getQuId, temp.getId());
                    List<Option> examQuAnswers = optionMapper.selectList(optionWrapper);
                    // Check whether it's correct
                    examRecordDetailVO.setIsRight(1);
                    for (Option temp1 : examQuAnswers) {
                        boolean containsBanana = opIds.contains(temp.getId());
                        if (containsBanana) {
                            // If any answer is not correct, it's judged as incorrect
                            examRecordDetailVO.setIsRight(0);
                        }
                    }
                }
                case 3 -> {
                    // Query user's selected options
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getId, examQuAnswer.getAnswerId());
                    Option op1 = optionMapper.selectOne(optionLambdaQueryWrapper);
                    examRecordDetailVO.setMyOption(Integer.toString(op1.getSort()));
                    // Check whether it's correct
                    Option byId = optionService.getById(examQuAnswer.getAnswerId());
                    if (byId.getIsRight() == 1) {
                        examRecordDetailVO.setIsRight(1);
                    } else {
                        examRecordDetailVO.setIsRight(0);
                    }
                }
                case 4 -> {
                    examRecordDetailVO.setMyOption(examQuAnswer.getAnswerContent());
                    examRecordDetailVO.setIsRight(-1);
                }
                default -> {
                }
            }
            examRecordDetailVOS.add(examRecordDetailVO);
        }
        return Result.success("Query successful", examRecordDetailVOS);
    }


    @Override
    public Result<IPage<ExerciseRecordVO>> getExerciseRecordPage(Integer pageNum, Integer pageSize ,String repoName) {
        // Create page object
        Page<Repo> repoPage = new Page<>(pageNum, pageSize);
        // Query the IDs of exercises the user has taken
        LambdaQueryWrapper<UserExerciseRecord> userExerciseRecordWrapper = new LambdaQueryWrapper<>();
        userExerciseRecordWrapper.eq(UserExerciseRecord::getUserId, SecurityUtil.getUserId());
        List<UserExerciseRecord> userExerciseRecord = userExerciseRecordMapper.selectList(userExerciseRecordWrapper);
        List<Integer> repoIds = userExerciseRecord.stream()
                .map(UserExerciseRecord::getRepoId)
                .collect(Collectors.toList());
        // Query information about exercises from the repo table
        LambdaQueryWrapper<Repo> examWrapper = new LambdaQueryWrapper<>();
        examWrapper.in(Repo::getId, repoIds)
                .like(StringUtils.isNotBlank(repoName),Repo::getTitle,repoName);
        Page<Repo> exercisePageResult = repoMapper.selectPage(repoPage, examWrapper);
        // Entity conversion
        Page<ExerciseRecordVO> exerciseRecordVOPage = recordConverter.pageRepoEntityToVo(exercisePageResult);
        return Result.success("Query successful", exerciseRecordVOPage);
    }


    @Override
    public Result<List<ExerciseRecordDetailVO>> getExerciseRecordDetail(Integer exerciseId) {
        // 1. Question Stem
        // 2. Options
        // 3. User's Answer
        // 4. Correct Answer
        // 5. Whether Correct
        // 6. Question Analysis

        List<ExerciseRecordDetailVO> exerciseRecordDetailVOS = new ArrayList<>();

        // Query questions for the exercise
        LambdaQueryWrapper<Question> questionLambdaQueryWrapper = new LambdaQueryWrapper<>();
        questionLambdaQueryWrapper.eq(Question::getRepoId, exerciseId);
        List<Question> questions = questionMapper.selectList(questionLambdaQueryWrapper);

        for (Question temp : questions) {
            ExerciseRecordDetailVO exerciseRecordDetailVO = new ExerciseRecordDetailVO();
            exerciseRecordDetailVO.setImage(temp.getImage());
            exerciseRecordDetailVO.setTitle(temp.getContent());
            exerciseRecordDetailVO.setAnalyse(temp.getAnalysis());
            exerciseRecordDetailVO.setQuType(temp.getQuType());

            // Query options for the question
            LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
            optionWrapper.eq(Option::getQuId, temp.getId());
            List<Option> options = optionMapper.selectList(optionWrapper);

            if (temp.getQuType() == 4) {
                exerciseRecordDetailVO.setOption(null);
            } else {
                exerciseRecordDetailVO.setOption(options);
            }

            if (temp.getQuType() == 4 && !options.isEmpty()) {
                exerciseRecordDetailVO.setRightOption(options.get(0).getContent());
            } else {
                List<Integer> correctOptionSorts = options.stream()
                        .filter(option -> option.getIsRight() == 1)
                        .map(Option::getSort)
                        .collect(Collectors.toList());
                String correctOptionString = correctOptionSorts.stream()
                        .map(String::valueOf)
                        .collect(Collectors.joining(","));
                exerciseRecordDetailVO.setRightOption(correctOptionString);
            }

            LambdaQueryWrapper<ExerciseRecord> exerciseRecordLambdaQueryWrapper = new LambdaQueryWrapper<>();
            exerciseRecordLambdaQueryWrapper.eq(ExerciseRecord::getUserId, SecurityUtil.getUserId())
                    .eq(ExerciseRecord::getRepoId, exerciseId)
                    .eq(ExerciseRecord::getQuestionId, temp.getId());
            ExerciseRecord exerciseRecord = exerciseRecordMapper.selectOne(exerciseRecordLambdaQueryWrapper);

            // If the user didn't answer the question
            if (exerciseRecord == null) {
                exerciseRecordDetailVO.setMyOption(null);
                exerciseRecordDetailVO.setIsRight(-1);
                exerciseRecordDetailVOS.add(exerciseRecordDetailVO);
                continue;
            }

            switch (temp.getQuType()) {
                case 1 -> {
                    // Set user's answer
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getId, exerciseRecord.getAnswer());
                    Option selectedOption = optionMapper.selectOne(optionLambdaQueryWrapper);
                    exerciseRecordDetailVO.setMyOption(Integer.toString(selectedOption.getSort()));

                    // Set whether the answer is correct
                    Option correctOption = optionService.getById(exerciseRecord.getAnswer());
                    exerciseRecordDetailVO.setIsRight(correctOption.getIsRight());
                }
                case 2 -> {
                    // Parse the answer ID to a list
                    List<Integer> answerIds = Arrays.stream(exerciseRecord.getAnswer().split(","))
                            .map(Integer::parseInt)
                            .collect(Collectors.toList());

                    // Get the sort order of each selected option
                    List<String> selectedOptionSorts = options.stream()
                            .filter(option -> answerIds.contains(option.getId()))
                            .map(option -> Integer.toString(option.getSort()))
                            .collect(Collectors.toList());

                    String myOption = String.join(",", selectedOptionSorts);
                    exerciseRecordDetailVO.setMyOption(myOption);

                    // Find the correct answers
                    List<Integer> correctAnswerIds = options.stream()
                            .filter(option -> option.getIsRight() == 1)
                            .map(Option::getId)
                            .collect(Collectors.toList());

                    // Determine whether the answer is correct
                    exerciseRecordDetailVO.setIsRight(correctAnswerIds.equals(answerIds) ? 1 : 0);
                }
                case 3 -> {
                    // Set user's answer
                    LambdaQueryWrapper<Option> optionLambdaQueryWrapper = new LambdaQueryWrapper<>();
                    optionLambdaQueryWrapper.eq(Option::getId, exerciseRecord.getAnswer());
                    Option selectedOption = optionMapper.selectOne(optionLambdaQueryWrapper);
                    exerciseRecordDetailVO.setMyOption(Integer.toString(selectedOption.getSort()));

                    // Set whether the answer is correct
                    Option correctOption = optionService.getById(exerciseRecord.getAnswer());
                    exerciseRecordDetailVO.setIsRight(correctOption.getIsRight());
                }
                case 4 -> {
                    exerciseRecordDetailVO.setMyOption(exerciseRecord.getAnswer());
                    exerciseRecordDetailVO.setIsRight(-1);
                }
                default -> {}
            }

            exerciseRecordDetailVOS.add(exerciseRecordDetailVO);
        }

        return Result.success("Query successful", exerciseRecordDetailVOS);
    }

    @Override
    @Transactional
    public Result<QuestionVO> fillAnswer(ExerciseFillAnswerFrom exerciseFillAnswerFrom) {
        ExerciseRecord exerciseRecord = exerciseConverter.fromToEntity(exerciseFillAnswerFrom);
        // Default: user's answer is correct
        boolean flag = true;
        exerciseRecord.setIsRight(1);

        // Check correctness of objective questions
        if (exerciseFillAnswerFrom.getQuType() != 4) {
            List<Integer> options = Arrays.stream(exerciseRecord.getAnswer().split(","))
                    .map(Integer::parseInt).toList();
            List<Integer> rightOptions = new ArrayList<>();
            optionMapper.selectAllByQuestionId(exerciseRecord.getQuestionId()).forEach(option -> {
                if (option.getIsRight() == 1) {
                    rightOptions.add(option.getId());
                }
            });

            if (options.size() != rightOptions.size()) {
                flag = false;
            } else {
                for (Integer option : options) {
                    if (!rightOptions.contains(option)) {
                        flag = false;
                        exerciseRecord.setIsRight(0);
                        break;
                    }
                }
            }
        }

        if (flag) {
            exerciseRecord.setIsRight(1);
        } else {
            exerciseRecord.setIsRight(0);
        }

        // Check if it's the first time this question is attempted
        LambdaQueryWrapper<ExerciseRecord> exerciseRecordLambdaQueryWrapper = new LambdaQueryWrapper<ExerciseRecord>()
                .eq(ExerciseRecord::getUserId, SecurityUtil.getUserId())
                .eq(ExerciseRecord::getRepoId, exerciseRecord.getRepoId())
                .eq(ExerciseRecord::getQuestionId, exerciseRecord.getQuestionId());
        ExerciseRecord databaseExerciseRecord = exerciseRecordMapper.selectOne(exerciseRecordLambdaQueryWrapper);
        boolean exercised = Optional.ofNullable(databaseExerciseRecord).isEmpty();

        if (exercised) {
            // If the question hasn't been attempted before, add a new record
            exerciseRecordMapper.insert(exerciseRecord);
            // Get exercise record for this question repository
            LambdaQueryWrapper<UserExerciseRecord> exerciseRecordWrapper = new LambdaQueryWrapper<UserExerciseRecord>()
                    .eq(UserExerciseRecord::getUserId, SecurityUtil.getUserId())
                    .eq(UserExerciseRecord::getRepoId, exerciseRecord.getRepoId());
            UserExerciseRecord userExerciseRecord = userExerciseRecordMapper.selectOne(exerciseRecordWrapper);

            if (Optional.ofNullable(userExerciseRecord).isEmpty()) {
                // If it's the user's first attempt at this question repository, add a new record
                LambdaQueryWrapper<Question> questionWrapper = new LambdaQueryWrapper<Question>()
                        .eq(Question::getRepoId, exerciseRecord.getRepoId());
                int totalCount = questionMapper.selectCount(questionWrapper).intValue();
                UserExerciseRecord insertUserExerciseRecord = new UserExerciseRecord();
                insertUserExerciseRecord.setExerciseCount(1);
                insertUserExerciseRecord.setRepoId(exerciseRecord.getRepoId());
                insertUserExerciseRecord.setTotalCount(totalCount);
                userExerciseRecordMapper.insert(insertUserExerciseRecord);
            } else {
                // Update exercise count if it's not the first attempt
                LambdaQueryWrapper<Question> wrapper = new LambdaQueryWrapper<Question>()
                        .eq(Question::getId, exerciseRecord.getRepoId());
                UserExerciseRecord updateUserExerciseRecord = new UserExerciseRecord();
                updateUserExerciseRecord.setTotalCount(questionMapper.selectCount(wrapper).intValue());
                updateUserExerciseRecord.setId(userExerciseRecord.getId());
                updateUserExerciseRecord.setExerciseCount(userExerciseRecord.getExerciseCount() + 1);
                userExerciseRecordMapper.updateById(updateUserExerciseRecord);
            }
        } else {
            // If the question has been attempted before, update the answer
            exerciseRecord.setId(databaseExerciseRecord.getId());
            exerciseRecordMapper.updateById(exerciseRecord);
        }

        // Get question information and return it to the user
        QuestionVO questionVO = questionMapper.selectSingle(exerciseRecord.getQuestionId());

        // Different responses for different question types
        // Response for subjective questions
        if (exerciseRecord.getQuestionType() == 4) {
            return Result.success(null, questionVO);
        }

        if (flag) {
            return Result.success("Correct answer", questionVO);
        }
        return Result.success("Incorrect answer", questionVO);
    }

    @Override
    public Result<QuestionVO> getSingle(Integer id) {
        QuestionVO questionVO = questionMapper.selectDetail(id);
        return Result.success("Query successful", questionVO);
    }

    @Override
    public Result<AnswerInfoVO> getAnswerInfo(Integer repoId, Integer quId) {
        // Retrieve question information
        QuestionVO questionVO = questionMapper.selectSingle(quId);

        // Convert question VO to answer info VO
        AnswerInfoVO answerInfoVO = exerciseConverter.quVOToAnswerInfoVO(questionVO);

        // Query exercise record
        LambdaQueryWrapper<ExerciseRecord> exerciseRecordLambdaQueryWrapper = new LambdaQueryWrapper<ExerciseRecord>()
                .eq(ExerciseRecord::getRepoId, repoId)
                .eq(ExerciseRecord::getQuestionId, quId)
                .eq(ExerciseRecord::getUserId, SecurityUtil.getUserId());
        ExerciseRecord exerciseRecord = exerciseRecordMapper.selectOne(exerciseRecordLambdaQueryWrapper);

        // Set answer content in answer info VO
        answerInfoVO.setAnswerContent(exerciseRecord.getAnswer());

        // Return result based on whether the answer is correct
        if (exerciseRecord.getIsRight() == 1) {
            return Result.success("Answer correct", answerInfoVO);
        } else {
            return Result.success("Answer incorrect", answerInfoVO);
        }
    }

}