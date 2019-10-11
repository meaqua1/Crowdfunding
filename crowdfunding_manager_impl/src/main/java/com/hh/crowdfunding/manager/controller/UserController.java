package com.hh.crowdfunding.manager.controller;

import com.github.pagehelper.PageInfo;
import com.hh.crowdfunding.domain.Role;
import com.hh.crowdfunding.domain.User;
import com.hh.crowdfunding.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-11 14:04
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;
    @RequestMapping("/add")
    public String add() {
        return "user/add";
    }

    @RequestMapping("/findAll/{page}")
    public String findAll(@PathVariable("page") int page,
                          @RequestParam(value = "size", defaultValue = "5") int size,
                          Model model) {
        List<User> all = userService.findAll(page, size);
        PageInfo pageInfo = new PageInfo(all);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("find", "findAll");
        return "user/user";
    }

    @RequestMapping("/findByUsernameOrEmail/{page}")
    public String findByUsernameOrEmail(@PathVariable("page") int page,
                                        @RequestParam(value = "size", defaultValue = "5") int size,
                                        @RequestParam(value = "condition") String condition,
                                        @RequestParam(value = "cond") String cond,
                                        Model model) {
        List<User> users = userService.findByUsernameOrEmail(page, size, condition.trim(), cond);
        PageInfo pageInfo = new PageInfo(users);
        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("con", condition);
        model.addAttribute("cond", cond);
        model.addAttribute("find", "findByUsernameOrEmail");
        return "user/user";
    }

    //保存一个用户
    @ResponseBody
    @RequestMapping("/save")
    public boolean save(User user) {
        boolean isSuccess = userService.save(user);
        return isSuccess;
    }
    //回显数据并跳转到修改页面
    @RequestMapping(value = "/toUpdate/{id}")
    public String findById(@PathVariable("id") Integer id, Model Model) {
        User byId = userService.findById(id);
        Model.addAttribute("user",byId);
        return "user/edit";
    }
    //更新数据，成功返回true
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(User user) {
        return userService.update(user);
    }
    //删除数据，成功返回true
    @ResponseBody
    @RequestMapping("/delete")
    public boolean delete(Integer id) {
        return userService.deleteById(id);
    }
    //批量删除数据，成功返回true
    @ResponseBody
    @RequestMapping("/deleteBatch")
    public boolean deleteBatch(Integer[] id) {
        for (Integer uid : id) {
            boolean isSuccess = userService.deleteById(uid);
            if (!isSuccess){
                return false;
            }
        }
        return true;
    }
    //跳转分配角色页面
    @RequestMapping("/toAssignRole/{id}")
    public String assignRole(@PathVariable("id") Integer id,Model model) {
        List<Role> roles = userService.findByRoleIds(id);
        List<Role> noRoles = userService.findNoRoleByRoleIds(id);
        model.addAttribute("roles",roles);
        model.addAttribute("noRoles",noRoles);
        model.addAttribute("id",id);
        return "user/assignRole";
    }
    //分配角色，成功返回true
    @ResponseBody
    @RequestMapping("/assignRole")
    public boolean assignRole(@RequestParam(value = "roleIds",defaultValue = "0")Integer[] roleIds,@RequestParam("userId") Integer userId) {
        boolean isSuess = userService.assignRole(roleIds,userId);
        return true;
    }

/*    @ResponseBody
    @RequestMapping("/findAll")
    //异步提交
    public PageInfo findAll(int page,
                          int size,
                          Model model) {
        List<User> all = userService.findAll(1, 5);
        PageInfo pageInfo = new PageInfo(all);
        model.addAttribute("pageInfo", pageInfo);

        return pageInfo;
    }*/

}
