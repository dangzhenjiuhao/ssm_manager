package com.xjz.ssmmanager.common.controller;

import com.xjz.ssmmanager.common.execeptions.PageNotFoundException;
import com.xjz.ssmmanager.common.execeptions.ServerException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Controller
public class GlobalExceptionHandler {

    @ExceptionHandler({Exception.class})
    public String notFound(Exception ex){
        ex.printStackTrace();
        return "page/errors/404";
    }

    @ExceptionHandler({ServerException.class})
    public String serverError(ServerException ex){
        ex.printStackTrace();
        return "page/errors/404";
    }
}
