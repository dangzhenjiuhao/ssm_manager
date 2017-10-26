package com.xjz.ssmmanager.service.impl;

import com.xjz.ssmmanager.mapper.UserMapper;
import com.xjz.ssmmanager.pojo.User;
import com.xjz.ssmmanager.pojo.UserExample;
import com.xjz.ssmmanager.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service("userService")
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    public int countByExample(UserExample example) {
        return userMapper.countByExample(example);
    }

    public int deleteByExample(UserExample example) {
        return userMapper.deleteByExample(example);
    }

    public int deleteByPrimaryKey(Long id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    public int insert(User record) {
        return userMapper.insert(record);
    }

    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    public List<User> selectByExample(UserExample example) {
        return userMapper.selectByExample(example);
    }

    public User selectByPrimaryKey(Long id) {
        return userMapper.selectByPrimaryKey(id);
    }

    public int updateByExampleSelective(User record, UserExample example) {
        return userMapper.updateByExampleSelective(record,example);
    }

    public int updateByExample(User record, UserExample example) {
        return userMapper.updateByExample(record, example);
    }

    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }
}
