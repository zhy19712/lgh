package com.example.demo.controller;

import com.example.demo.entity.Sys_user;
import com.example.demo.mapper.Sys_userMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
class DemoControllerTest {

    @Autowired
    private Sys_userMapper sys_userMapper;

    @Test
    void test1() {
        System.out.println(("----- selectAll method test ------"));
        List<Sys_user> sysuserList = sys_userMapper.selectList(null);
        Assert.assertEquals(2, sysuserList.size());
        sysuserList.forEach(System.out::println);
    }
}