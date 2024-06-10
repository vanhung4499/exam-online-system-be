package com.hnv99.exam.service;

import com.hnv99.exam.common.result.Result;
import com.hnv99.exam.model.entity.UserBook;
import com.hnv99.exam.model.form.userbook.ReUserBookForm;
import com.hnv99.exam.model.vo.userbook.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 *  Service class for managing user error books
 */
public interface UserBookService extends IService<UserBook> {

    /**
     * Paginated query for exams with errors
     *
     * @param pageNum Page number
     * @param pageSize Number of records per page
     * @param examName Exam name
     * @return Result containing a paginated list of user error books
     */
    Result<IPage<UserPageBookVO>> getPage(Integer pageNum, Integer pageSize, String examName);

    /**
     * Query the list of error IDs in the error book for a specific exam
     *
     * @param examId Exam ID
     * @return Result containing a list of user exam book records
     */
    Result<List<ReUserExamBookVO>> getReUserExamBook(Integer examId);

    /**
     * Query a single question
     *
     * @param quId Question ID
     * @return Result containing a single question from the error book
     */
    Result<BookOneQuVO> getBookOne(Integer quId);

    /**
     * Fill in the answer
     *
     * @param reUserBookForm Form containing the answer details
     * @return Result containing the added answer information
     */
    Result<AddBookAnswerVO> addBookAnswer(ReUserBookForm reUserBookForm);
}
