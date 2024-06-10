package com.hnv99.exam.service.impl;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.converter.QuestionConverter;
import com.hnv99.exam.mapper.OptionMapper;
import com.hnv99.exam.mapper.QuestionMapper;
import com.hnv99.exam.model.entity.Option;
import com.hnv99.exam.model.entity.Question;
import com.hnv99.exam.model.form.question.QuestionExcelForm;
import com.hnv99.exam.model.form.question.QuestionForm;
import com.hnv99.exam.model.vo.QuestionVO;
import com.hnv99.exam.service.QuestionService;
import com.hnv99.exam.util.AliOSSUtil;
import com.hnv99.exam.util.SecurityUtil;
import com.hnv99.exam.util.excel.ExcelUtils;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.annotation.Resource;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements QuestionService {

    @Resource
    private QuestionConverter questionConverter;
    @Resource
    private QuestionMapper questionMapper;
    @Resource
    private OptionMapper optionMapper;
    @Resource
    private AliOSSUtil aliOSSUtil;


    @Override
    @Transactional
    public Result<String> addSingleQuestion(QuestionForm questionFrom) {
        // Input validation
        List<Option> options = questionFrom.getOptions();
        if (questionFrom.getQuType() != 4 && (Objects.isNull(options) || options.size() < 2)) {
            return Result.failed("Non-essay questions must have at least two options");
        }

        Question question = questionConverter.fromToEntity(questionFrom);
        questionMapper.insert(question);

        if (question.getQuType() == 4) {
            // For essay questions, add a single option
            Option option = questionFrom.getOptions().get(0);
            option.setQuId(question.getId());
            optionMapper.insert(option);
        } else {
            // For non-essay questions, add options in batch
            options.forEach(option -> option.setQuId(question.getId()));
            optionMapper.insertBatch(options);
        }
        return Result.success("Successfully added");
    }

    @Override
    @Transactional
    public Result<String> deleteBatchByIds(String ids) {
        List<Integer> list = Arrays.stream(ids.split(",")).map(Integer::parseInt).toList();
        // Delete options first
        optionMapper.deleteBatchByQuIds(list);
        // Then delete questions
        questionMapper.deleteBatchIds(list);
        return Result.success("Successfully deleted");
    }

    @Override
    public Result<IPage<QuestionVO>> pagingQuestion(Integer pageNum, Integer pageSize, String title, Integer type, Integer repoId) {
        IPage<QuestionVO> page = new Page<>(pageNum, pageSize);
        // Teachers can only view questions they created

        if ("role_teacher".equals(SecurityUtil.getRole())) {
            page = questionMapper.pagingQuestion(page, title, repoId, type, SecurityUtil.getUserId());
        } else {
            page = questionMapper.pagingQuestion(page, title, repoId, type, 0);
        }

        return Result.success("Query successful", page);
    }

    @Override
    public Result<QuestionVO> querySingle(Integer id) {
        return Result.success("Query successful", questionMapper.selectSingle(id));
    }

    @Override
    @Transactional
    public Result<String> updateQuestion(QuestionForm questionFrom) {
        // Update the question
        Question question = questionConverter.fromToEntity(questionFrom);
        questionMapper.updateById(question);
        // Update options
        List<Option> options = questionFrom.getOptions();
        for (Option option : options) {
            optionMapper.updateById(option);
        }
        return Result.success("Update successful");
    }

    @SneakyThrows(Exception.class)
    @Override
    @Transactional
    public Result<String> importQuestion(Integer id, MultipartFile file) {
        if (!ExcelUtils.isExcel(Objects.requireNonNull(file.getOriginalFilename()))) {
            return Result.failed("This file is not a valid Excel file");
        }
        List<QuestionExcelForm> questionExcelFroms = ExcelUtils.readMultipartFile(file, QuestionExcelForm.class);
        // Type conversion
        List<QuestionForm> list = QuestionExcelForm.convertToQuestionForm(questionExcelFroms);

        for (QuestionForm questionFrom : list) {
            Question question = questionConverter.fromToEntity(questionFrom);
            question.setRepoId(id);
            // Add single question and obtain its ID
            questionMapper.insert(question);
            // Batch add options
            List<Option> options = questionFrom.getOptions();
            final int[] count = {0};
            options.forEach(option -> {
                // For short answer questions, set the answer as correct by default
                if (question.getQuType() == 4) {
                    option.setIsRight(1);
                }
                option.setSort(++count[0]);
                option.setQuId(question.getId());
            });

            // Avoiding short answer questions without answers
            if (!options.isEmpty()) {
                optionMapper.insertBatch(options);
            }

        }

        return Result.success("Import successful");
    }


    @SneakyThrows(IOException.class)
    @Override
    public Result<String> uploadImage(MultipartFile file) {
        if (!aliOSSUtil.isImage(Objects.requireNonNull(file.getOriginalFilename()))) {
            return Result.failed("This file is not a commonly used image format (png, jpg, jpeg, bmp)");
        }
        if (aliOSSUtil.isOverSize(file)) {
            return Result.failed("Image size cannot exceed 50KB");
        }
        String url = aliOSSUtil.upload(file);
        if (StringUtils.isBlank(url)) {
            return Result.failed("Failed to upload image");
        }
        return Result.success("Image uploaded successfully", url);
    }

}
