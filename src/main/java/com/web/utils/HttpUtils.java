package com.web.utils;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;

/**
 * http工具类
 */
public class HttpUtils {
  /**
   * 返回json文本
   * @param response 
   * @param code 返回状态码
   */
  public static void responseJson(HttpServletResponse response, ApiResult apiResult) {
    response.setStatus(HttpServletResponse.SC_OK);
    response.setContentType("application/json; charset=utf-8");
    try {
      PrintWriter pw;
      JSONObject json = JSONObject.parseObject(JSONObject.toJSONString(apiResult));
      pw = response.getWriter();
      pw.write(json.toString());
      pw.flush();
      pw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}