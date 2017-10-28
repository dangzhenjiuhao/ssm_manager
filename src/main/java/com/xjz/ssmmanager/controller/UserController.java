package com.xjz.ssmmanager.controller;

import com.xjz.ssmmanager.common.execeptions.ParameterException;
import com.xjz.ssmmanager.common.pojo.MessageResult;
import com.xjz.ssmmanager.common.pojo.TransData;
import com.xjz.ssmmanager.pojo.User;
import com.xjz.ssmmanager.pojo.UserExample;
import com.xjz.ssmmanager.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
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
        user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
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
    public MessageResult getList(int pageSize, int currentPage) {
        pageSize = pageSize == 0 ? 10 : pageSize;
        currentPage = currentPage == 0 ? 1 : currentPage;
        TransData<User> data = userService.getPageList(currentPage, pageSize);
        MessageResult<User> result = new MessageResult<User>();
        result.setStatusCode(200);
        result.setData(data);
        return result;
    }

    @RequestMapping("listkeyword")
    @ResponseBody
    public MessageResult getListByKeyWord(Integer pageSize, Integer currentPage, String keyWord) {
        pageSize = pageSize == null ? 10 : pageSize;
        currentPage = currentPage == null ? 1 : currentPage;
        TransData<User> data = userService.getPageList(currentPage, pageSize, keyWord);
        MessageResult<User> result = new MessageResult<User>();
        result.setStatusCode(200);
        result.setData(data);
        return result;
    }

    @RequestMapping("delete")
    @ResponseBody
    public MessageResult delete(Integer id) {
        if (null == id) {
            throw new ParameterException("参数异常");
        }
        int result = userService.deleteByPrimaryKey(id);
        MessageResult messageResult = new MessageResult();
        if (result > 0) {
            messageResult.setStatusCode(200);
        } else {
            messageResult.setStatusCode(500);
        }
        return messageResult;
    }

    @RequestMapping("deletes")
    @ResponseBody
    public MessageResult deletes(Integer[] ids) {
        if (null == ids) {
            throw new ParameterException("参数异常");
        }
        int result = userService.deleteByBatch(ids);
        MessageResult messageResult = new MessageResult();
        if (result > 0) {
            messageResult.setStatusCode(200);
        } else {
            messageResult.setStatusCode(500);
        }
        return messageResult;
    }

    @RequestMapping("updateStatus")
    @ResponseBody
    public MessageResult updateStatus(Integer id, Byte status) {
        if (null == id || null == status) {
            throw new ParameterException("参数异常");
        }
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        int result = userService.updateStatus(user);
        MessageResult messageResult = new MessageResult();
        if (result > 0) {
            messageResult.setStatusCode(200);
        } else {
            messageResult.setStatusCode(500);
        }
        return messageResult;
    }
}
