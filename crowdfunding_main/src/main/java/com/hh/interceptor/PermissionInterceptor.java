package com.hh.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Set;

/**
 * @author hh
 * @create 2019-09-18 15:37
 */
@Component
public class PermissionInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(PermissionInterceptor.class);
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        logger.info("开始拦截权限");
        Set<String> allUrl = (Set<String>) request.getSession().getServletContext().getAttribute("allUrl");
        String servletPath = request.getServletPath();
        if (servletPath.indexOf("/", 1)!=-1){
            servletPath = servletPath.substring(0, servletPath.indexOf("/", 1));
        }
        if (allUrl.contains(servletPath)){
            Set<String> myUrl = (Set<String>) request.getSession().getAttribute("myUrl");
            if (myUrl.contains(servletPath)){
                logger.info("拥有权限..放行");
                return true;
            } else {
                logger.info("没有权限...返回登录界面");
                response.sendRedirect(request.getContextPath()+"/login");
                return false;
            }
        } else {
            logger.info("不拦截此地址，放行");
            return true;
        }
    }
}
