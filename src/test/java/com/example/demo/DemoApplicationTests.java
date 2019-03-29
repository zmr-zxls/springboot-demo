package com.example.demo;

import com.web.entry.Admin;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ComponentScan(basePackages = "com.web.entry") // 告诉spring自动扫描的包
public class DemoApplicationTests {
    @Autowired
    private Admin admin;
    @Test
    public void contextLoads() {
    }

    /**
     * 测试application.properties
     * 以及自动装配
     */
    @Test
    public void adminTest() {
        Assert.assertEquals(admin.getName(), "chenhaijun");
        Assert.assertEquals(admin.getMail(), "test@qq.com");
    }
}
