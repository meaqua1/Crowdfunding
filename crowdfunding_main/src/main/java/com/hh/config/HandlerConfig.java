package com.hh.config;

import com.hh.interceptor.LoginInterceptor;
import com.hh.interceptor.PermissionInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author hh
 * @create 2019-09-18 14:14
 */
@Configuration
public class HandlerConfig implements WebMvcConfigurer {
    @Autowired
    private LoginInterceptor loginInterceptor;
    @Autowired
    private PermissionInterceptor permissionInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //addInterceptor:表示哪个拦截器类
        //addPathPatterns:设置拦截路径
        registry.addInterceptor(loginInterceptor).addPathPatterns("/**")
                //excludePathPatterns:设置不拦截的路径
                .excludePathPatterns("/login","/doLogin","/logout","/user/reg","/user/toReg","/index","/",
                        "/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**","/script/**","/ztree/**");
        //权限拦截器
        registry.addInterceptor(permissionInterceptor).addPathPatterns("/**")
                //excludePathPatterns:设置不拦截的路径
                .excludePathPatterns("/main","/login","/doLogin","/logout","/user/reg","/user/toReg","/index","/",
                        "/bootstrap/**","/css/**","/fonts/**","/img/**","/jquery/**","/script/**","/ztree/**");

    }
}
