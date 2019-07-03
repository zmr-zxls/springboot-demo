package com.web.validator.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.validation.Constraint;
import javax.validation.Payload;

import com.web.validator.hanlder.CardTypeValidatorHanlder;

@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER})
@Constraint(validatedBy = CardTypeValidatorHanlder.class)
/**
 * 卡片类型校验注解类
 * @see CardTypeValidatorHanlder
 * @author zmr-zxls
 */
public @interface CardTypeValidator {
  String message() default "非法卡片类型";
  Class<?>[] groups() default {};
  Class<? extends Payload>[] payload() default {};
}