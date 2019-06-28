package com.web.exceptions;

/**
 * 重复异常类
 */
public class RepeatException extends RuntimeException {
  private static final long serialVersionUID = 1L;
  private static final String DEFAUL_MESSAGE = "已存在";
  public RepeatException(String message) {
    super(message);
  }
  public RepeatException() {
    super(DEFAUL_MESSAGE);
  }
}