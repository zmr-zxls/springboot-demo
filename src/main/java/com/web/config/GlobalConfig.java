package com.web.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 此类用来配置全局的常量
 */
@Component
@ConfigurationProperties(prefix = "custom")
public class GlobalConfig {
  // 静态变量注入使用方法
  public static String COOKIE_ID;
  // 静态变量注入使用方法
  @Value("${custom.cookie.id}")
  public void setCOOKIE_ID(String cOOKIE_ID) {
    GlobalConfig.COOKIE_ID = cOOKIE_ID;
  }
}