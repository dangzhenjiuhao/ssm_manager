package com.xjz.test;

import com.xjz.ssmmanager.pojo.User;
import com.xjz.ssmmanager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import javax.sql.DataSource;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath*:spring/spring-mybatis.xml"})
public class UserServiceTest {

    @Resource
    private UserService userService;

    @Resource
    private DataSource dataSource;

    @Test
    public void test() {
        //User user = userService.selectByPrimaryKey(1L);
        //System.out.println(user.getUsername());
    }

    @Test
    public void testDataSource(){
        System.out.println(dataSource);
    }
}
