package com.xjz.ssmmanager.common.handlers;

import com.xjz.ssmmanager.common.pojo.MessageResult;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;


public class CustomExceptionHandler {
    @ExceptionHandler({HttpMessageNotReadableException.class})
    public MessageResult badRequest(HttpMessageNotReadableException ex){
        ex.printStackTrace();
        MessageResult result = new MessageResult(400,null,"参数异常");
        return result;
    }

    @ExceptionHandler
    public MessageResult badRequest(Exception ex){
        ex.printStackTrace();
        MessageResult result = new MessageResult(400,null,"参数异常");
        return result;
    }
}
