package com.web.validator.hanlder;

// import java.lang.invoke.MethodHandles;
import java.util.regex.Pattern;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.web.exceptions.OutOfRangeException;
import com.web.validator.annotation.PasswordValidator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

// import org.hibernate.validator.internal.util.logging.Log;
// import org.hibernate.validator.internal.util.logging.LoggerFactory;

public class PasswordValidatorHanlder implements ConstraintValidator<PasswordValidator, String> {

  private static final Pattern WORD = Pattern.compile("[a-zA-Z]");
  private static final Pattern DIGIT = Pattern.compile("\\d");
  private static final Pattern NOT_WORD_DIGIT = Pattern.compile("[^a-zA-Z0-9]");
  // private static final Log LOG = LoggerFactory.make(MethodHandles.lookup());
  private static final Logger Log = LoggerFactory.getLogger(PasswordValidatorHanlder.class);
  private int level;
  private int max;
  @Override
  public void initialize(PasswordValidator constraintAnnotation) {
    this.level = constraintAnnotation.level();
    this.max = constraintAnnotation.max();
  }
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return checkValidate(value, level);
  }
  public boolean checkValidate(String value, int level) {
    if (value == null || "".equals(value)) {
      return false;
    }
    if (value.length() > max) {
      // throw LOG.getInvalidRangeException(value.length(), max);
      Log.error("【密码长度超出范围】实际长度:{} > 最大长度:{}", value.length(), max);
      throw new OutOfRangeException("密码长度超出范围");
    }
    switch(level) {
      case 0: return value.length() > 0;
      case 1: return value.length() >= 3;
      case 2: return value.length() >=6 && WORD.matcher(value).find() && DIGIT.matcher(value).find();
      case 3: return value.length() >=9 && WORD.matcher(value).find() && DIGIT.matcher(value).find()
        && NOT_WORD_DIGIT.matcher(value).find();
    }
    return false;
  }
}