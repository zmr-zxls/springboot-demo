package com.example.aop;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("com.example.aop")
@EnableAspectJAutoProxy
/**
 * 注解形式的配置类
 */
public class AopConfig {
}
