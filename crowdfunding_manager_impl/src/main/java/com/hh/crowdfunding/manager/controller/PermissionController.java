package com.hh.crowdfunding.manager.controller;

import com.hh.crowdfunding.domain.Permission;
import com.hh.crowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author hh
 * @create 2019-09-15 15:04
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/permission")
    public String permission() {
        return "permission/permission";
    }
    @RequestMapping("/toAdd/{pid}")
    public String toAdd(@PathVariable("pid") Integer pid, Model model) {
        model.addAttribute("pid",pid);
        return "permission/add";
    }
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id, Model model) {
        Permission byId = permissionService.findById(id);
        model.addAttribute("permission",byId);
        return "permission/update";
    }
    @ResponseBody
    @RequestMapping("/loadData")
    public Object loadData(){
        //要返回的根集合
        List<Permission> root = new ArrayList<Permission>();
        //从数据库查询所有
        List<Permission> childredPermissons =  permissionService.queryAllPermission();

        Map<Integer,Permission> map = new HashMap<Integer,Permission>();
        //遍历集合存入map中，map的key就是数据库的id
        for (Permission innerpermission : childredPermissons) {
            map.put(innerpermission.getId(), innerpermission);
        }

        for (Permission permission : childredPermissons) {
            //通过子查找父
            //子菜单
            Permission child = permission ; //假设为子菜单
            if(child.getPid() == null ){
                //默认展开父节点
                child.setOpen(true);
                root.add(permission);
            }else{
                //得到父节点
                Permission parent = map.get(child.getPid());
                //默认展开父节点
                parent.setOpen(true);
                //将子节点下入到父节点下
                parent.getChildren().add(child);
            }
        }
        return root;
    }
    @ResponseBody
    @RequestMapping("/save")
    public boolean save(Permission permission) {
        boolean isSuccess = permissionService.save(permission);
        return isSuccess;
    }
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(Permission permission) {
        boolean isSuccess = permissionService.update(permission);
        return isSuccess;
    }
    @ResponseBody
    @RequestMapping("/delete")
    public boolean delete(Integer id) {
        boolean isSuccess = permissionService.delete(id);
        return isSuccess;
    }
}
