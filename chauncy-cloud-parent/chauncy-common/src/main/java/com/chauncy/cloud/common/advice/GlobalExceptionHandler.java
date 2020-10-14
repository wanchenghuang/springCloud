package com.chauncy.cloud.common.advice;

import com.chauncy.cloud.common.base.Result;
import com.chauncy.cloud.common.enums.system.exception.Code;
import com.chauncy.cloud.common.exception.BusinessException;
import com.chauncy.cloud.common.exception.FeignException;
import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.multipart.MultipartException;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * @Author cheng
 * @create 2020-03-05 15:30
 *
 * 全局异常处理
 *
 * 当指定 {@code @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)} 时,
 * Feign 调用时 springboot 会自动抛出运行时异常, 会使得调用方无法获取 Result
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Result handleHttpMessageNotReadableException(HttpMessageNotReadableException ex,
                                                        HttpServletRequest request) {
        Code enums = Code.BAD_REQUEST;
        Throwable cause = ex.getCause();
        StringBuilder error = new StringBuilder();
        if(cause instanceof InvalidFormatException){
            InvalidFormatException tmp = (InvalidFormatException) cause;
            log.info("{} {} {} {}",tmp.getPathReference(),tmp.getProcessor(),tmp.getValue(),tmp.getTargetType());
            String path = tmp.getPathReference();
            error.append("属性").append(path.substring(path.lastIndexOf(".")+1))
                    .append("转换异常,要求是").append(tmp.getTargetType()).append("传入是")
                    .append(tmp.getValue());
        } else {
            enums = Code.BODY_MISS;
            error.append(ex.getMessage());
            log.error(ex.getMessage(), ex);
        }
        return Result.error(enums.getCode(), error.toString(),request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Result handleMissingServletRequestParameterException(MissingServletRequestParameterException e,
                                                                HttpServletRequest request) {

        StringBuilder error = new StringBuilder(Code.BAD_REQUEST.getMsg())
                .append(":参数").append(e.getParameterName()).append("缺失");
        log.info("{} {} {}",e.getParameterName(),e.getParameterType(), e.getMessage());
        return Result.error(Code.BAD_REQUEST, error.toString(),request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result handleMethodArgumentNotValidException(MethodArgumentNotValidException e,
                                                        HttpServletRequest request) {
        List<FieldError> fieldErrors = e.getBindingResult().getFieldErrors();
        StringBuilder builder = new StringBuilder(128);
        for (FieldError fieldError : fieldErrors) {
            builder.append("【")
                    .append(fieldError.getField())
                    .append("】")
                    .append("为【").append(fieldError.getRejectedValue()).append("】")
                    .append(fieldError.getDefaultMessage())
                    .append(";");
        }
        if (builder.length() <= 0) {
            builder.append(Code.BAD_REQUEST.getMsg());
        }
        log.error("参数校验失败: {}", builder.toString());
        return Result.error(Code.BAD_REQUEST, builder.toString(), request.getRequestURI());
    }

    @ExceptionHandler(value = HttpRequestMethodNotSupportedException.class)
    public Object httpRequestMethodNotSupportedExceptionHandler(HttpServletRequest request, HttpRequestMethodNotSupportedException ex) {
        Code code = Code.METHOD_NOT_ALLOWED;
        StringBuilder error = new StringBuilder(code.getMsg()).append(":")
                .append("'").append(ex.getMethod()).append("'不支持,请用")
                .append(Arrays.toString(ex.getSupportedMethods()));
        log.info("{}:{}", error.toString(), ex.getMessage());
        return Result.error(code, error.toString(), request.getRequestURI());
    }

    @ExceptionHandler(value = MethodArgumentTypeMismatchException.class)
    public @ResponseBody Object methodArgumentTypeMismatchExceptionExceptionHandler(HttpServletRequest req, MethodArgumentTypeMismatchException ex) {
        Code code = Code.ARGUMENT_NOT_VALID;
        log.info("ex.getValue():{}, ex.getName():{}, ex.getPropertyName():{}, ex.getRequiredType():{}, ex.getParameter():{}",
                ex.getValue(),ex.getName(),ex.getPropertyName(),ex.getRequiredType().getName(),ex.getParameter());
        StringBuilder error = new StringBuilder(code.getMsg())
                .append(":参数").append(ex.getName())
                .append("要求类型为").append(ex.getRequiredType().getSimpleName())
                .append(",传入的为").append(ex.getValue());
        return Result.error(code, error.toString(), req.getRequestURI());
    }

    @ExceptionHandler(NullPointerException.class)
    public @ResponseBody Object handlerNullPointerException(NullPointerException ex, HttpServletRequest req) {
        log.error(ex.getMessage(), ex);
        return Result.error(Code.NULL_PARAM, Code.NULL_PARAM.getMsg(), req.getRequestURI());
    }

    @ExceptionHandler(value = BindException.class)
    public @ResponseBody Object bindExceptionHandler(HttpServletRequest req, BindException ex) {
        Code code = Code.ARGUMENT_NOT_VALID;
        StringBuilder error = new StringBuilder(code.getMsg()).append(":");
        for(FieldError fe:ex.getFieldErrors()){
            error.append(fe.getField()).append(" ").append(fe.getDefaultMessage()).append(",");
            log.info("FieldError [{}] [{}] [{}] [{}] [{}]", fe.getObjectName(), fe.getCode(), fe.getField(),fe.getDefaultMessage(), fe.getRejectedValue());
        }
        return Result.error(code, error.toString(), req.getRequestURI());
    }

    /*@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e,
                                                               HttpServletRequest request) {
        log.error("不支持当前请求方法", e);
        return Result.error(Code.METHOD_NOT_ALLOWED, "不支持当前请求方法",request.getRequestURI());
    }*/

    @ResponseStatus(HttpStatus.UNSUPPORTED_MEDIA_TYPE)
    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public Result handleHttpRequestMethodNotSupportedException(HttpMediaTypeNotSupportedException e,
                                                               HttpServletRequest request) {
        log.error("不支持当前媒体类型", e);
        return Result.error(Code.UNSUPPORTED_MEDIA_TYPE, "不支持当前媒体类型",request.getRequestURI());
    }

    /**
     * 当指定 {@code @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)} 时, feign 调用时 springboot 会自动抛出运行时异常
     * 会使调用方无法获取 Result
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(FeignException.class)
    public Result handleFeignException(FeignException e) {
        log.error("服务间调用异常", e);
        return e.getResult();
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(BusinessException.class)
    public Result handleHttpRequestMethodNotSupportedException(BusinessException e,
                                                               HttpServletRequest request) {
        log.error("业务异常", e);
        return Result.error(e.getCode(), e.getMessage(), request.getRequestURI());
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    public Result handleException(Exception e, HttpServletRequest request) {
        log.error("服务繁忙", e);
        return Result.error(Code.ERROR, request.getRequestURI());
    }

    @ExceptionHandler(value = {DuplicateKeyException.class})
    public Result duplicateKeyException(DuplicateKeyException ex) {
        log.error("primary key duplication exception:{}", ex.getMessage());
        return Result.error(Code.DUPLICATE_PRIMARY_KEY);
    }

    @ExceptionHandler(value = {MultipartException.class})
    public Result uploadFileLimitException(MultipartException ex) {
        log.error("upload file size limit:{}", ex.getMessage());
        return Result.error(Code.UPLOAD_FILE_SIZE_LIMIT);
    }
}
