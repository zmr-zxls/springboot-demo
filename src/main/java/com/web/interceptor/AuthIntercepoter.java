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

/**
 * 登录权限拦截
 * @see InterceptorConfig 由该类配置拦截规则
 */
public class AuthIntercepoter implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(AuthIntercepoter.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        // 获取由客户端发送来的jsessionid
        HttpSession session = request.getSession(false);
        if (session == null) {
            String acceptType = request.getHeader("Accept");
            String loginUrl = "/user/toLogin";
            LOG.info("未登录,重定向{}", loginUrl);
            // 如果接受json格式返回，json数据，以防前端ajax请求，而后端重定向导致客户端报错
            if (acceptType != null && acceptType.contains("application/json")) {
                HttpUtils.responseJson(response, ApiResult.status(Status.NOT_LOGIN, loginUrl));
            } else {
                // 如果不是没有声明接受json，默认重定向
                response.sendRedirect(loginUrl);
            }
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
