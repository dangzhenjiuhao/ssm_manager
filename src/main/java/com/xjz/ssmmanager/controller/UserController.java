package com.xjz.ssmmanager.controller;

import com.xjz.ssmmanager.common.pojo.MessageResult;
import com.xjz.ssmmanager.pojo.User;
import com.xjz.ssmmanager.pojo.UserExample;
import com.xjz.ssmmanager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import sun.plugin2.message.Message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @RequestMapping(value = "add", method = {RequestMethod.POST})
    @ResponseBody
    public MessageResult<User> add(User user, HttpServletRequest request) {
        user.setRegTime(new Date());
        user.setRegIp(request.getRemoteHost());
        user.setLastLoginTime(new Date());
        user.setLastLoginIp(request.getRemoteHost());
        user.setUpdateTime(new Date());

        int result = userService.insert(user);
        MessageResult<User> message = new MessageResult<User>();
        if (result > 0) {
            message.setStatusCode(200);
        } else {
            message.setStatusCode(500);
        }
        return message;
    }

    @RequestMapping("list")
    @ResponseBody
    public List<User> getList() {
        UserExample example = new UserExample();
        List<User> users = userService.selectByExample(example);
        return users;
    }
}
