package com.xjz.ssmmanager.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xjz.ssmmanager.common.pojo.MessageResult;
import com.xjz.ssmmanager.common.pojo.TransData;
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


    @Override
    public int countByExample(UserExample example) {
        return userMapper.countByExample(example);
    }

    @Override
    public int deleteByExample(UserExample example) {
        return userMapper.deleteByExample(example);
    }

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(User record) {
        return userMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return userMapper.insertSelective(record);
    }

    @Override
    public List<User> selectByExample(UserExample example) {
        return userMapper.selectByExample(example);
    }

    @Override
    public User selectByPrimaryKey(Integer id) {
        return userMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByExampleSelective(User record, UserExample example) {
        return userMapper.updateByExampleSelective(record,example);
    }

    @Override
    public int updateByExample(User record, UserExample example) {
        return userMapper.updateByExample(record, example);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return userMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return userMapper.updateByPrimaryKey(record);
    }

    @Override
    public TransData<User> getPageList(int currentPage, int pageSize) {
        //设置分页信息
        PageHelper.startPage(currentPage,pageSize);
        //执行查询
        UserExample example = new UserExample();
        List<User> list = userMapper.selectByExample(example);
        //取分页信息
        PageInfo<User> pageInfo = new PageInfo<User>(list);
        //创建返回结果对象
        TransData<User> result = new TransData<User>();
        int allCount = (int)pageInfo.getTotal();
        result.setAllCount(allCount);
        result.setDatas(list);
        return result;
    }
}
