package com.hnv99.exam.common.handler;


import com.hnv99.exam.common.exception.AppException;
import com.hnv99.exam.common.result.Result;
import jakarta.validation.ConstraintViolationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import java.sql.SQLIntegrityConstraintViolationException;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * Handle custom exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(AppException.class)
    public Result<String> handleAppException(AppException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed(e.getLocalizedMessage());
    }

    /**
     * Handle parameter validation exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<String> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error(e.getMessage(), e.getClass());
        String message = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        return Result.failed(message);
    }

    /**
     * Handle unique constraint exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(SQLIntegrityConstraintViolationException.class)
    public Result<String> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed("Duplicate entry");
    }

    /**
     * Handle request parameter parse exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result<String> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed("Unable to parse request parameters");
    }

    /**
     * Handle missing request parameter exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result<String> handleMissingServletRequestParameterException(MissingServletRequestParameterException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed(e.getParameterName() + " is required");
    }

    /**
     * Handle primary key conflict exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public Result<String> handleDuplicateKeyException(DuplicateKeyException e) {
        String name = e.getMessage().split(":")[2].split(" ")[3];
        log.error(e.getMessage(), e.getClass());
        return Result.failed("Primary key conflict: " + name + " already exists");
    }

    /**
     * Handle access denied exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(AccessDeniedException.class)
    public Result<String> handleAccessDeniedException(AccessDeniedException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed("You do not have access to this resource");
    }

    /**
     * Handle file too large exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public Result<String> handlerMaxUploadSizeExceededException(MaxUploadSizeExceededException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed("File too large, maximum upload size is 5MB");
    }

    /**
     * Handle file not found exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(MissingServletRequestPartException.class)
    public Result<String> handlerMissingServletRequestPartException(MissingServletRequestPartException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed("File not found");
    }

    /**
     * Handle constraint violation exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(ConstraintViolationException.class)
    public Result<String> handleConstraintViolationException(ConstraintViolationException e) {
        log.error(e.getMessage(), e.getClass());
        return Result.failed(e.getMessage());
    }

    /**
     * Handle other exceptions
     *
     * @param e Exception
     * @return Response
     */
    @ExceptionHandler(Exception.class)
    public Result<String> handleException(Exception e) {
        log.error(e.getMessage(), e.getClass(), e.getCause());
        return Result.failed("Unknown error");
    }
}
