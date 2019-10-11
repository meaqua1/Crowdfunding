package com.hh.listener;

import com.hh.crowdfunding.domain.Permission;
import com.hh.crowdfunding.manager.service.PermissionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author hh
 * @create 2019-09-18 22:43
 */
@WebListener
public class StartListener implements ServletContextListener {
    private static final Logger logger = LoggerFactory.getLogger(StartListener.class);
    @Autowired
    private PermissionService permissionService;
    //服务器启动时，创建application对象需要执行的方法
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext application = sce.getServletContext();

        Set<String> allUrl = new HashSet<>();
        List<Permission> allPermission = permissionService.queryAllPermission();
        for (Permission permission : allPermission) {
            String url = permission.getUrl();
            if (url!=null&&url.contains("/")){
                url=url.substring(0,url.indexOf("/"));
            }
            allUrl.add("/"+url);
        }
        application.setAttribute("allUrl",allUrl);
        logger.info(allUrl.toString());
    }
}
