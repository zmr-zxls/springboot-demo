package com.web.exceptions;

/**
 * 不存在该用户异常类
 * 运行时异常，非检查类，不需要捕获
 */
public class NoSuchUserException extends RuntimeException{
  private static final long serialVersionUID = 1L;
  private static final String DEFAULT_MESSAGE = "不存在该用户";
  public NoSuchUserException(String message) {
    super(message);
  }
  public NoSuchUserException() {
    super(DEFAULT_MESSAGE);
  }
}