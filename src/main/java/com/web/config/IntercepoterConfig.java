package com.web.config;

import com.web.interceptor.AuthIntercepoter;
import com.web.interceptor.LogInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 拦截器注入
 * 1.日志拦截器
 * 2.授权登陆拦截器
 */
@Configuration
public class IntercepoterConfig implements WebMvcConfigurer {
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 静态资源不要拦截
        registry.addInterceptor(new LogInterceptor()).excludePathPatterns("/static/**");
        registry.addInterceptor(new AuthIntercepoter())
            .excludePathPatterns(
                "/static/**",
                "/user/toLogin",
                "/user/login",
                "/error",
                "/user/regsiter"
            );
    }
}