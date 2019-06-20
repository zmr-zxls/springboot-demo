package com.web.utils;
/**
 * api接口返回数据格式
 * @author zxls-zmr
 * @since 2019-06-20
 */
public class ApiResult {
  private int code;
  private String message;
  private Object data;
  
  // 状态枚举类
  public enum Status {
    SUCCESS(200, "OK"),
    BAD_REQUEST(400, "Bad Request"),
    NOT_FOUND(404, "Not Found"),
    INTERNAL_SERVER_ERROR(500, "Unknown Internal Error"),
    NOT_VALID_PARAM(40005, "Not valid Params"),
    NOT_SUPPORTED_OPERATION(40006, "Operation not supported"),
    NOT_LOGIN(50000, "Not Login"),
    LOGIN_FAILED(50001, "Login Failed"),
    FAILED(-1, "FAILED");
    // 状态码
    private int code;
    // 状态内容
    private String message;

    Status(int code, String message) {
      this.code = code;
      this.message = message;
    }

    public int getCode() {
      return code;
    }

    public void setCode(int code) {
      this.code = code;
    }

    public String getMessage() {
      return message;
    }

    public void setMessage(String message) {
      this.message = message;
    }
  }

  public int getCode() {
    return code;
  }

  public void setCode(int code) {
    this.code = code;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Object getData() {
    return data;
  }

  public void setData(Object data) {
    this.data = data;
  }

  public ApiResult(int code, String message, Object data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public ApiResult(int code, String message) {
    this.code = code;
    this.message = message;
    this.data = null;
  }
  public static ApiResult success(Object data) {
    return new ApiResult(Status.SUCCESS.getCode(), Status.SUCCESS.getMessage(), data);
  }

  public static ApiResult message(int code, String message) {
    return new ApiResult(code, message);
  }

  public static ApiResult status(Status status) {
    return new ApiResult(status.getCode(), status.getMessage());
  }

  public static ApiResult fail(String message) {
    return new ApiResult(Status.FAILED.getCode(), message);
  }
}