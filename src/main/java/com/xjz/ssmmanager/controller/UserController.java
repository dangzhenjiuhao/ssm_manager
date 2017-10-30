package com.xjz.ssmmanager.controller;

import com.xjz.ssmmanager.common.execeptions.ParameterException;
import com.xjz.ssmmanager.common.pojo.MessageResult;
import com.xjz.ssmmanager.common.pojo.TransData;
import com.xjz.ssmmanager.pojo.User;
import com.xjz.ssmmanager.pojo.UserExample;
import com.xjz.ssmmanager.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Controller;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;
import sun.plugin2.message.Message;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
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
    public MessageResult getList(@RequestParam(value="pageSize",required = false,defaultValue = "10") int pageSize,@RequestParam(value="currentPage",required = false,defaultValue = "1") int currentPage, @RequestParam(value="keyWord",required = false,defaultValue = "") String keyWord) {
        TransData<User> data = null;
        try {
            keyWord = new String(keyWord.getBytes("ISO-8859-1"),"UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(keyWord)){
            data = userService.getPageList(currentPage, pageSize);
        }else{
            data = userService.getPageList(currentPage, pageSize, keyWord);
        }
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

    @RequestMapping("exists")
    @ResponseBody
    public int checkUserIsExists(String userName){
        int result = userService.checkIsExists(userName);
        return result;
    }
}
