package com.hpugs.satoken.aop;

import cn.dev33.satoken.exception.NotLoginException;
import cn.dev33.satoken.exception.SaTokenException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @Author：xinge
 * @Date：2024/11/12 13:57
 * @Description：
 */
@RestControllerAdvice
public class GlobalExceptionAdvice {

    @ExceptionHandler({SaTokenException.class})
    public String handlerException(SaTokenException e) {
        if(e instanceof NotLoginException){
            return "请先登录";
        }
        return e.getMessage();
    }

}
