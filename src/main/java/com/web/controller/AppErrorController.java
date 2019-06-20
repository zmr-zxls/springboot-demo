package com.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.web.utils.ApiResult;
import com.web.utils.ApiResult.Status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
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
  public String errorPageHandler(HttpServletRequest request, HttpServletResponse response, Exception e, Model model) {
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
      default: errorMsg = "请求无效";
    }
    model.addAttribute("message", errorMsg);
    LOG.info("code:{}, messge:{}", status, errorMsg);
    return "report";
  }
  /**
   * 除Web页面外的错误处理，比如Json/XML等
   */
  @RequestMapping(value = ERROR_PATH)
  @ResponseBody
  @ExceptionHandler(value = {Exception.class})
  public ApiResult errorApiHandler(HttpServletRequest request, final Exception ex) {
    LOG.info(ex.getMessage()+"------------------"+ex.getStackTrace());
    // Map<String, Object> attr = this.errorAttributes.getErrorAttributes(req, false);
    // int status = getStatus(request);
    return ApiResult.status(Status.INTERNAL_SERVER_ERROR);
  }
}