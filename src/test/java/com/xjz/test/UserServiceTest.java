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
        int id = 5;
        byte status = 1;
        User user = new User();
        user.setId(id);
        user.setStatus(status);
        int result = userService.updateStatus(user);
        System.out.println(result);
    }

    @Test
    public void testDataSource(){
        System.out.println(dataSource);
    }

    @Test
    public void testDeleteByBatch(){
        Integer[] ids = {7,8};
        int i = userService.deleteByBatch(ids);
        System.out.println(i);
    }
}
