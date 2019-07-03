package com.web.validator.hanlder;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.web.validator.annotation.CardTypeValidator;

/**
 * 卡片类型校验处理器
 * @see CardTypeValidator
 * @author zmr-zxls
 */
public class CardTypeValidatorHanlder implements ConstraintValidator<CardTypeValidator, String> {
  private final static String[] TYPES = new String[]{
    "储蓄卡",
    "信用卡",
    "公积金卡",
    "社保卡"
  };
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    for (String type : TYPES) {
      if (type.equals(value)) {
        return true;
      }
    }
    return false;
  }
  
}