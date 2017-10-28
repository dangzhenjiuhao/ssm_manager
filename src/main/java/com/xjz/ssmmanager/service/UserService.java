package com.xjz.ssmmanager.service;

import com.xjz.ssmmanager.common.pojo.MessageResult;
import com.xjz.ssmmanager.common.pojo.TransData;
import com.xjz.ssmmanager.pojo.User;
import com.xjz.ssmmanager.pojo.UserExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    int countByExample(UserExample example);

    int deleteByExample(UserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    int insertSelective(User record);

    List<User> selectByExample(UserExample example);

    User selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") User record, @Param("example") UserExample example);

    int updateByExample(@Param("record") User record, @Param("example") UserExample example);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    TransData<User> getPageList(int currentPage, int pageSize);

    TransData<User> getPageList(int currentPage, int pageSize, String keyWord);

    int updateStatus(User user);

    int deleteByBatch(Integer[] ids);
}

