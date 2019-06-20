package com.example.annotation;

import java.lang.reflect.Field;

/**
 * @desc 注解处理器
 * <p>通过反射获取注解</p>
 */
public class CountryAnnotationHanlder {
  public static void getCountryName (Class<?> clazz) {
    // 获取已声明的成员变量
    Field[] fields = clazz.getDeclaredFields();
    // 遍历成员变量
    for (Field field : fields) {
      // 是否有CountryName的注解
      if (field.isAnnotationPresent(CountryName.class)) {
        // 通过反射获得注解对象
        CountryName countryName = (CountryName)field.getAnnotation(CountryName.class);
        System.out.println(countryName.value());
      }
    }
  }
}