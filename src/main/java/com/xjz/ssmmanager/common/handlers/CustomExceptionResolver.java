package com.xjz.ssmmanager.common.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xjz.ssmmanager.common.execeptions.ParameterException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

public class CustomExceptionResolver extends SimpleMappingExceptionResolver {

    private static final Logger logger = LoggerFactory.getLogger(CustomExceptionResolver.class);

    @Override
    public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        String viewName = determineViewName(ex, request);
        // vm方式返回

        if (viewName != null && !(request.getHeader("accept").indexOf("application/json") > -1 || (request
                .getHeader("X-Requested-With") != null && request
                .getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1))) {
            // 非异步方式返回
            Integer statusCode = determineStatusCode(request, viewName);
            if (statusCode != null) {
                applyStatusCodeIfPossible(request, response, statusCode);
            }
            // 将异常栈信息记录到日志中
            logger.error(ex.getMessage());
            // 设置跳转到提示页面
            ModelAndView mv = getModelAndView(viewName, ex, request);
            // 错误提示，根据需求添加
            mv.addObject("message", "系统异常！");
            // 跳转到提示页面
            return mv;
        } else {
            // 异步方式返回
            // 将异常栈信息记录到日志中
            logger.error(ex.getMessage());
            try {
                response.setContentType("application/json;charset=UTF-8");
                PrintWriter writer = response.getWriter();
                ObjectMapper mapper = new ObjectMapper(); //转换器
                // 错误提示，根据需求添加
                Map map = new HashMap();
                map.put("statusCode", 500);
                map.put("message", ex.getMessage());
                map.put("data",null);
                writer.write(mapper.writeValueAsString(map));
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            // 不进行页面跳转
            return null;
        }
    }
}
