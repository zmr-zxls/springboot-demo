package com.web.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Springboot 配置类
 * 默认首页配置,前端SPA装载页面
 */
@Configuration
public class ViewController implements WebMvcConfigurer {
    @Override
    public void addViewControllers( ViewControllerRegistry registry ) {
        registry.addViewController( "/" ).setViewName( "static/index.html" );
    }
//    @Bean
//    public MultipartResolver multipartResolver () {
//        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver();
//        multipartResolver.setMaxUploadSize(1000 * 1000);
//        return multipartResolver;
//    }
}
