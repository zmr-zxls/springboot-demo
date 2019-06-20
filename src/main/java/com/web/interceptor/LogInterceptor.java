package com.web.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LogInterceptor implements HandlerInterceptor {
    private static final Logger LOG = LoggerFactory.getLogger(LogInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        String url = request.getRequestURL().toString();
        String ip = request.getRemoteAddr();
        String userAgent = request.getHeader("user-agent");
        LOG.info("request--> [url]:{}, [ip]:{}, [userAgent]:{}", url, ip, userAgent);
        return true;
    }

    // @Override
    // public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
    //         ModelAndView modelAndView) throws Exception {
    //     // System.out.println(handler.getClass().getClassLoader().);
    //     HandlerMethod method = (HandlerMethod) handler;
    //     LOG.info("response--> [HandlerClass]{}", method.getBean().getClass().getName());
    // }
}
