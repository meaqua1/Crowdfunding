package com.hh.controller;

import com.hh.crowdfunding.domain.Permission;
import com.hh.crowdfunding.domain.User;
import com.hh.crowdfunding.exception.LoginFailException;
import com.hh.crowdfunding.manager.service.PermissionService;
import com.hh.crowdfunding.manager.service.UserService;
import com.hh.crowdfunding.utils.MD5Util;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author hh
 * @create 2019-09-09 22:38
 */
@Controller
public class DispatcherController {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/index")
    public String index() {
        return "index";
    }

    @RequestMapping("/login")
    public String login(HttpSession session) {
        return "login";
    }

    @RequestMapping("/main")
    public String main(HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Permission> myPermission = permissionService.findByUserId(user.getId());

        Permission permissionRoot = null;
        Map<Integer,Permission> map = new HashMap<Integer,Permission>();

        Set<String> myUrl = new HashSet<>();//用于拦截器拦截url

        //遍历集合存入map中，map的key就是数据库的id
        for (Permission innerpermission : myPermission) {
            map.put(innerpermission.getId(), innerpermission);
            String url = innerpermission.getUrl();
            if (url!=null&&url.contains("/")){
                url=url.substring(0,url.indexOf("/"));
            }
            myUrl.add("/"+url);
        }
        session.setAttribute("myUrl",myUrl);

        for (Permission permission : myPermission) {
            //通过子查找父
            //子菜单
            Permission child = permission ; //假设为子菜单
            if(child.getPid() == null ){
                //默认展开父节点
                child.setOpen(true);
                permissionRoot = permission;
            }else{
                //得到父节点
                Permission parent = map.get(child.getPid());
                //默认展开父节点
                parent.setOpen(true);
                //将子节点下入到父节点下
                parent.getChildren().add(child);
            }
        }
        session.setAttribute("myPermission",permissionRoot);
        return "main";
    }


    @RequestMapping("/logout")
    public String logout(HttpSession session) {
        //销毁session
        session.invalidate();
        return "redirect:/login";
    }

    @ResponseBody
    @RequestMapping("/doLogin")
    //异步请求登录
    public boolean doLogin(String loginacct, String userpswd, String type, HttpSession session) {
        Map<String,Object> loginInfo = new HashMap<>();
        loginInfo.put("loginacct",loginacct);
        loginInfo.put("userpswd", MD5Util.digest(userpswd));
        loginInfo.put("type",type);
        User user = null;
        try {
            user = userService.login(loginInfo);
        } catch (LoginFailException e) {
            e.printStackTrace();
            return false;
        }
        session.setAttribute("user",user);
        return true;
    }

/*    @RequestMapping("/doLogin")
    //同步请求
    public String doLogin(String loginacct, String userpswd, String type, Model model, HttpSession session) {
        Map<String,Object> loginInfo = new HashMap<>();
        loginInfo.put("loginacct",loginacct);
        loginInfo.put("userpswd",userpswd);
        loginInfo.put("type",type);
        User user = null;
        try {
            user = userService.login(loginInfo);
        } catch (LoginFailException e) {
            e.printStackTrace();
            model.addAttribute("login_err",e.getMessage());
            return "forward:login";
        }
        session.setAttribute("user",user);
        return "redirect:main";
    }*/
}
