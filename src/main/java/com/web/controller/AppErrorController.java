package com.web.controller;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;

import com.web.exceptions.NoSuchUserException;
import com.web.exceptions.RepeatException;
import com.web.utils.ApiResult;
import com.web.utils.ApiResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

/**
 * 全局异常处理 搭配 ExceptionHandler 可以实现自定义响应拦截
 * <p>
 * 1.参数校验异常处理
 * <p>
 * 2.其他异常处理
 */
@Controller
@RestControllerAdvice
@ControllerAdvice
public class AppErrorController implements ErrorController {
  private static final Logger LOG = LoggerFactory.getLogger(AppErrorController.class);
  private static final String ERROR_PATH = "/error";

  @Override
  public String getErrorPath() {
    return ERROR_PATH;
  }

  /**
   * Web页面错误处理
   */
  @RequestMapping(value = ERROR_PATH, produces = "text/html")
  @ExceptionHandler(value = Exception.class)
  public ModelAndView errorPageHandler(HttpServletRequest request, HttpServletResponse response, Exception e,
      ModelAndView model) {
    int status = response.getStatus();
    String errorMsg;
    switch (status) {
    case 403:
      errorMsg = "请求被禁止";
      break;
    case 404:
      errorMsg = "未找到请求的资源";
      break;
    case 500:
      errorMsg = "服务器内部错误";
      break;
    case 405:
      errorMsg = "请求类型不正确，或者参数不正确";
      break;
    default:
      errorMsg = "请求无效";
    }
    model.getModel().put("message", errorMsg);
    LOG.info("code:{}, messge:{}", status, errorMsg);
    model.setViewName("report");
    return model;
  }

  /**
   * 除Web页面外的错误处理，比如Json/XML等
   */
  @RequestMapping(value = ERROR_PATH)
  public ApiResult errorApiHandler(HttpServletRequest request, HttpServletResponse response, final Exception ex) {
    int status = response.getStatus();
    LOG.info("code:{}, messge:{}", status, ex.getMessage());
    switch (status) {
    case 403:
      return ApiResult.status(Status.FORBiDDEN_REQUEST);
    case 404:
      return ApiResult.status(Status.NOT_FOUND);
    case 500:
      return ApiResult.status(Status.INTERNAL_SERVER_ERROR);
    case 405:
      return ApiResult.status(Status.NOT_Acceptable);
    default:
      break;
    }
    return ApiResult.status(Status.FAILED);
  }

  /**
   * 处理参数校验异常
   * 
   * @param ex
   * @return
   */
  @ExceptionHandler(ConstraintViolationException.class)
  public ApiResult resolveConstraintViolationException(ConstraintViolationException ex) {
    Set<ConstraintViolation<?>> constraintViolations = ex.getConstraintViolations();
    String msg = "参数校验失败";
    if (!constraintViolations.isEmpty()) {
      StringBuilder msgBuilder = new StringBuilder();
      for (ConstraintViolation<?> constraintViolation : constraintViolations) {
        msgBuilder.append(constraintViolation.getMessage()).append(",");
      }
      msg = msgBuilder.toString();
      if (msg.length() > 1) {
        msg = msg.substring(0, msg.length() - 1);
      }
    }
    return ApiResult.fail(msg);
  }

  /**
   * 处理方法参数绑定异常
   * 
   * @param ex
   * @return
   */
  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ApiResult resolveMehotdArgumentException(MethodArgumentNotValidException ex) {
    List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
    return ApiResult.fail(getErrorMessages(objectErrors, "参数校验失败"));
  }

  /**
   * 参数绑定到对象异常
   * 
   * @param ex
   * @return
   */
  @ExceptionHandler(BindException.class)
  public ApiResult resolveRepeatException(BindException ex) {
    List<ObjectError> objectErrors = ex.getBindingResult().getAllErrors();
    return ApiResult.fail(getErrorMessages(objectErrors, "数绑定失败"));
  }

  private String getErrorMessages(List<ObjectError> errors, String DefaultMsg) {
    String msg = DefaultMsg;
    if (!errors.isEmpty()) {
      StringBuilder msgBuilder = new StringBuilder();
      for (ObjectError objectError : errors) {
        msgBuilder.append(objectError.getDefaultMessage()).append(",");
      }
      msg = msgBuilder.toString();
      if (msg.length() > 1) {
        msg = msg.substring(0, msg.length() - 1);
      }
    }
    return msg;
  }
  /**
   * 具有一般的异常，统一处理
   * <p>1.springboot  - 参数缺失异常
   * <p>2.自定义的异常 - 用户不存在
   * <p>3.自定义的异常 - 重复异常
   * @param ex 异常类
   * @return
   */
  @ExceptionHandler({
    MissingServletRequestParameterException.class,
    NoSuchUserException.class,
    RepeatException.class
  })
  public ApiResult resolveCommonException(Exception ex) {
    return ApiResult.fail(ex.getMessage());
  }
  // /**
  //  * 处理自定义的异常 - 用户不存在
  //  * @param ex
  //  * @return
  //  */
  // @ExceptionHandler(NoSuchUserException.class)
  // public ApiResult resolveNoSuchUserException(NoSuchUserException ex) {
  //   return ApiResult.fail(ex.getMessage());
  // }
  // /**
  //  * 处理自定义的异常 - 重复异常
  //  * @param ex
  //  * @return
  //  */
  // @ExceptionHandler(RepeatException.class)
  // public ApiResult resolveRepeatException(RepeatException ex) {
  //   return ApiResult.fail(ex.getMessage());
  // }
}