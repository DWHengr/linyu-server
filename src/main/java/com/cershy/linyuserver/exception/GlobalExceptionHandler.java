package com.cershy.linyuserver.exception;

import com.cershy.linyuserver.utils.ResultUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * @author: dwh
 **/
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 捕获未处理异常
     */
    @ExceptionHandler(value = Exception.class)
    public Object handleException(Exception e, HttpServletRequest request) {
        log.error("未处理异常 -> {}", e.getClass());
        log.error("url -> {}", request.getRequestURL());
        log.error("msg -> {}", e.getMessage());
        log.error("stack trace -> {}", e.getStackTrace());
        return ResultUtil.Fail("Internal service error");
    }

    /**
     * 捕获未处理异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public Object validationException(MethodArgumentNotValidException e, HttpServletRequest request) {
        log.error("未处理异常 -> {}", e.getClass());
        log.error("url -> {}", request.getRequestURL());
        log.error("msg -> {}", e.getBindingResult().getFieldError().getDefaultMessage());
        log.error("stack trace -> {}", e.getStackTrace());
        return ResultUtil.Fail(e.getBindingResult().getFieldError().getDefaultMessage());
    }


    /**
     * 自定义异常
     */
    @ExceptionHandler(value = com.cershy.linyuserver.exception.LinyuException.class)
    public Object LinyuException(LinyuException e, HttpServletRequest request) {
        log.error("自定义异常 -> {}", e.getClass());
        log.error("url -> {}", request.getRequestURL());
        log.error("msg -> {}", e.getMessage());
        log.error("stack trace -> {}", e.getStackTrace());
        if (null != e.paramToString())
            log.error("exception param -> {}", e.paramToString());
        e.empty();
        return ResultUtil.Result(e.getCode(), e.getMessage());
    }
}
