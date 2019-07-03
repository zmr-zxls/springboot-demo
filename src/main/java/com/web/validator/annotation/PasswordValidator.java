package com.web.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.web.validator.hanlder.PasswordValidatorHanlder;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Constraint(validatedBy = PasswordValidatorHanlder.class)
public @interface PasswordValidator {
  String message() default "{com.web.validator.annotation.PasswordValidator.message}";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
  /**
   * 密码等级
   * <li>0: 简单模式 长度>=1  包含数字 | 字母
   * <li>1: 简单模式 长度>=3  包含数字 | 字母
   * <li>2: 正常模式 长度>=6 包含数字 & 字母
   * <li>3: 复杂模式 长度>=12 包含数字 & 字母 & 特殊字符
   */
  int level() default 0;
  /**
   * 密码的默认最大长度
   */
  int max() default 16;
}