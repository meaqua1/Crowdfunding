package com.hh.interceptor;

import com.hh.crowdfunding.domain.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author hh
 * @create 2019-09-18 13:52
 */
@Component
public class LoginInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LoginInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("拦截器开始拦截...");
        //判断用户是否登录
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            logger.info("登录，放行...");
            return true;
        } else {
//            request.getSession().setAttribute("loginerr","您没有登录，请先登录");
            response.sendRedirect(request.getContextPath()+"/login");
            logger.info("未登录，返回登录...");
            return false;
        }
    }
}
