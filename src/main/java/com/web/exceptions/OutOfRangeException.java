package com.web.exceptions;

/**
 * 范围超出溢出
 */
public class OutOfRangeException extends RuntimeException {

  private static final long serialVersionUID = 1L;
  private static final String DEFALT_MSG = "超出范围";
  public OutOfRangeException(String msg) {
    super(msg);
  }
  public OutOfRangeException() {
    super(DEFALT_MSG);
  }
}