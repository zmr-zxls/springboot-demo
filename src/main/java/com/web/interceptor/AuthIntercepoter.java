package com.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.web.utils.ApiResult;
import com.web.utils.HttpUtils;
import com.web.utils.ApiResult.Status;

public class AuthIntercepoter implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AuthIntercepoter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        HttpSession session = request.getSession(false);
        if (session == null) {
            LOG.info("未登录,重定向{}", "/user/toLogin");
            response.sendRedirect("/user/toLogin");
            // returnJson(response, HttpServletResponse.SC_OK);
            // HttpUtils.responseJson(response, ApiResult.status(Status.NOT_LOGIN));
            return false;
        }
        LOG.info("授权校验通过");
        return true;
    }
    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    //         ModelAndView modelAndView) throws Exception {
    //     System.out.println("controller 处理完成");
    // }

    // @Override
    // public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
    //         throws Exception {
    //     System.out.println("完成后");
    // }
}
