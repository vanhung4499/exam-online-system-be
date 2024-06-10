package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.UserBookConverter;
import com.hnv99.exam.mapper.ExamQuAnswerMapper;
import com.hnv99.exam.mapper.OptionMapper;
import com.hnv99.exam.mapper.QuestionMapper;
import com.hnv99.exam.mapper.UserBookMapper;
import com.hnv99.exam.model.entity.*;
import com.hnv99.exam.model.form.userbook.ReUserBookForm;
import com.hnv99.exam.model.vo.userbook.*;
import com.hnv99.exam.service.OptionService;
import com.hnv99.exam.service.QuestionService;
import com.hnv99.exam.service.UserBookService;
import com.hnv99.exam.util.SecurityUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class UserBookServiceImpl extends ServiceImpl<UserBookMapper, UserBook> implements UserBookService {
    @Resource
    private UserBookMapper userBookMapper;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private ExamQuAnswerMapper examQuAnswerMapper;
    @Resource
    private UserBookConverter userBookConverter;
    @Resource
    private QuestionService questionService;
    @Resource
    private OptionService optionService;

    @Override
    public Result<IPage<UserPageBookVO>> getPage(Integer pageNum, Integer pageSize, String examName) {
        Page<UserPageBookVO> page = new Page<>(pageNum, pageSize);
        Page<UserPageBookVO> userPageBookVOPage = userBookMapper.selectPageVo(page, examName, SecurityUtil.getUserId());
        return Result.success("Query successful", userPageBookVOPage);
    }

    @Override
    public Result<List<ReUserExamBookVO>> getReUserExamBook(Integer examId) {
        // Query exam questions by exam ID
        LambdaQueryWrapper<UserBook> userBookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        userBookLambdaQueryWrapper.eq(UserBook::getExamId, examId)
                .eq(UserBook::getUserId, SecurityUtil.getUserId());
        List<UserBook> userBook = userBookMapper.selectList(userBookLambdaQueryWrapper);
        // Entity conversion
        List<ReUserExamBookVO> reUserExamBookVOS = userBookConverter.listEntityToVo(userBook);
        return Result.success("Query successful", reUserExamBookVOS);
    }

    @Override
    public Result<BookOneQuVO> getBookOne(Integer quId) {
        BookOneQuVO bookOneQuVO = new BookOneQuVO();
        // Get question
        Question quById = questionService.getById(quId);
        // Basic information
        bookOneQuVO.setImage(quById.getImage());
        bookOneQuVO.setContent(quById.getContent());
        bookOneQuVO.setQuType(quById.getQuType());
        // Answer list
        LambdaQueryWrapper<Option> optionLambdaQuery = new LambdaQueryWrapper<>();
        optionLambdaQuery.eq(Option::getQuId, quId);
        List<Option> list = optionMapper.selectList(optionLambdaQuery);
        bookOneQuVO.setAnswerList(list);
        return Result.success("Query successful", bookOneQuVO);
    }

    @Override
    public Result<AddBookAnswerVO> addBookAnswer(ReUserBookForm reUserBookForm) {
        // Create a response view
        AddBookAnswerVO addBookAnswerVO = new AddBookAnswerVO();
        // Not answered
        if (StringUtils.isBlank(reUserBookForm.getAnswer())) {
            return Result.success("Not answered");
        }
        // Query question type
        LambdaQueryWrapper<Question> quWrapper = new LambdaQueryWrapper<>();
        quWrapper.eq(Question::getId, reUserBookForm.getQuId());
        Question qu = questionMapper.selectOne(quWrapper);
        Integer quType = qu.getQuType();
        // Set question analysis
        addBookAnswerVO.setAnalysis(qu.getAnalysis());
        // Set correct answer
        LambdaQueryWrapper<Option> opWrapper = new LambdaQueryWrapper<>();
        opWrapper.eq(Option::getQuId, reUserBookForm.getQuId());
        List<Option> options = optionMapper.selectList(opWrapper);
        String current = "";
        ArrayList<Integer> strings = new ArrayList<>();
        for (Option temp : options) {
            if (temp.getIsRight() == 1) {
                strings.add(temp.getSort());
            }
        }
        List<String> stringList = strings.stream().map(String::valueOf).collect(Collectors.toList());
        String result = String.join(",", stringList);
        if(quType == 4){
            addBookAnswerVO.setRightAnswers(options.get(0).getContent());
        }else{
            addBookAnswerVO.setRightAnswers(result);
        }

        // Determine correctness and remove correct questions
        return switch (quType) {
            case 1 -> {
                Option byId = optionService.getById(reUserBookForm.getAnswer());
                if (byId.getIsRight() == 1) {
                    LambdaQueryWrapper<UserBook> userBookWrapper = new LambdaQueryWrapper<>();
                    userBookWrapper.eq(UserBook::getUserId, SecurityUtil.getUserId())
                            .eq(UserBook::getExamId, reUserBookForm.getExamId())
                            .eq(UserBook::getQuId, reUserBookForm.getQuId());
                    int row = userBookMapper.delete(userBookWrapper);
                    addBookAnswerVO.setCorrect(1);
                    yield Result.success("Correct answer, removed from the error book", addBookAnswerVO);
                } else {
                    addBookAnswerVO.setCorrect(0);
                    yield Result.success("Wrong answer", addBookAnswerVO);
                }
            }
            case 2 -> {
                // Find correct answers
                LambdaQueryWrapper<Option> optionWrapper = new LambdaQueryWrapper<>();
                optionWrapper.eq(Option::getIsRight, 1)
                        .eq(Option::getQuId, reUserBookForm.getQuId());
                List<Option> examQuAnswers = optionMapper.selectList(optionWrapper);
                // Analyze user answers
                List<Integer> quIds = Arrays.stream(reUserBookForm.getAnswer().split(","))
                        .map(Integer::parseInt)
                        .toList();
                // Determine correctness
                for (Option temp : examQuAnswers) {
                    boolean containsBanana = quIds.contains(temp.getId());
                    if (containsBanana) {
                        yield Result.success("Wrong answer");
                    }
                }
                LambdaQueryWrapper<UserBook> userBookWrapper = new LambdaQueryWrapper<>();
                userBookWrapper.eq(UserBook::getUserId, SecurityUtil.getUserId())
                        .eq(UserBook::getExamId, reUserBookForm.getExamId())
                        .eq(UserBook::getQuId, reUserBookForm.getQuId());
                userBookMapper.delete(userBookWrapper);
                yield Result.success("Correct answer");
            }
            case 3 -> {
                Option byId = optionService.getById(reUserBookForm.getAnswer());
                if (byId.getIsRight() == 1) {
                    LambdaQueryWrapper<UserBook> userBookWrapper = new LambdaQueryWrapper<>();
                    userBookWrapper.eq(UserBook::getUserId, SecurityUtil.getUserId())
                            .eq(UserBook::getExamId, reUserBookForm.getExamId())
                            .eq(UserBook::getQuId, reUserBookForm.getQuId());
                    userBookMapper.delete(userBookWrapper);
                    addBookAnswerVO.setCorrect(1);
                    yield Result.success("Correct answer, removed from the error book", addBookAnswerVO);
                } else {
                    addBookAnswerVO.setCorrect(0);
                    yield Result.success("Wrong answer", addBookAnswerVO);
                }
            }
            case 4 -> {
                if("1".equals(reUserBookForm.getAnswer())){
                    LambdaQueryWrapper<UserBook> userBookWrapper = new LambdaQueryWrapper<>();
                    userBookWrapper.eq(UserBook::getUserId, SecurityUtil.getUserId())
                            .eq(UserBook::getExamId, reUserBookForm.getExamId())
                            .eq(UserBook::getQuId, reUserBookForm.getQuId());
                    userBookMapper.delete(userBookWrapper);
                    yield Result.success("Correct answer, removed from the error book", addBookAnswerVO);
                }
                addBookAnswerVO.setCorrect(0);
                yield Result.success("Wrong answer", addBookAnswerVO);
            }
            default -> {
                yield Result.failed("Request error, please contact the administrator for assistance");
            }
        };
    }

}
