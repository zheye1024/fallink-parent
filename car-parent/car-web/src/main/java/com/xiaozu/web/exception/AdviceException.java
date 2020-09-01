package com.xiaozu.web.exception;

import com.xiaozu.core.exception.BusinessException;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.ConnectException;

/**
 * @Author:yanxiao
 * @Des:全局异常处理
 * @Date:2017/11/14
 */
@Log4j2
@RestControllerAdvice
public class AdviceException {

    @ExceptionHandler(value = Exception.class)
    public Object exceptionHandler(@RequestBody Object object, HttpServletRequest request, HttpServletResponse response, Exception e) {

        String path = request.getRequestURI();
        log.info("请求path={}异常", path, e);
        Throwable cause = e.getCause();
        if (cause instanceof ConnectException
                || cause instanceof java.net.SocketTimeoutException) {
            //feign调用错误
            response.setStatus(400);
            return "请求连接无效!";
        }
        response.setStatus(500);
        return "请求异常！";
    }

    @ExceptionHandler(value = BusinessException.class)
    public Object SysAppExceptionHandler(HttpServletRequest request, HttpServletResponse response, BusinessException e) {
        String path = request.getRequestURI();
        log.warn("业务请求path={}异常", path, e);
        response.setStatus(Integer.valueOf(e.getCode()));
        return e.getMsg();
    }


}
