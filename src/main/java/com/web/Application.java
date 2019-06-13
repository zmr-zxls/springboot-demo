package com.web;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @SpringBootApplication 会自动扫描同目录下所有的Bean

/**
 * 自动扫描的Bean包括注解上的
 * @Service
 * @Component
 * @Controller
 * @Respository
 *
 * @SpringBootApplication 是Spring Boot的核心注解，它是一个组合注解
 */
@SpringBootApplication
/*
    @EnableAutoConfiguration
    @ComponentScan(basePackages = "com.example")
*/
public class Application implements ApplicationRunner{
    public static void main(String[] args) {
        System.out.println(">>> Application starting....");
        SpringApplication.run(Application.class, args);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("Application Runner: started success!");
    }
}

