package com.hh.crowdfunding.manager.controller;

import com.hh.crowdfunding.domain.Permission;
import com.hh.crowdfunding.domain.Role;
import com.hh.crowdfunding.manager.service.PermissionService;
import com.hh.crowdfunding.manager.service.RoleService;
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
@RequestMapping("/role")
public class RoleController {
    @Autowired
    private RoleService roleService;
    @Autowired
    private PermissionService permissionService;
    @RequestMapping("/role")
    public String role(Model model) {
        List<Role> list = roleService.findAll();
        model.addAttribute("Roles",list);
        return "role/role";
    }
    @RequestMapping("/toAdd")
    public String toAdd() {
        return "role/add";
    }
    @RequestMapping("/toUpdate/{id}")
    public String toUpdate(@PathVariable("id") Integer id,Model model) {
        Role role = roleService.findById(id);
        model.addAttribute("role",role);
        return "role/update";
    }
    @RequestMapping("/toAssignPermission/{id}")
    public String toAssignPermission(@PathVariable("id") Integer id,Model model) {
        model.addAttribute("id",id);
        return "role/assignPermission";
    }
    @ResponseBody
    @RequestMapping("/loadDataAsync/{id}")//异步加载
    public Object loadDataAsync(@PathVariable("id") Integer RoleId){
        List<Integer> permissionIds = permissionService.findByRoleId(RoleId);
        //要返回的根集合
        List<Permission> root = new ArrayList<Permission>();
        //从数据库查询所有
        List<Permission> childredPermissons =  permissionService.queryAllPermission();

        Map<Integer,Permission> map = new HashMap<Integer,Permission>();
        //遍历集合存入map中，map的key就是数据库的id
        for (Permission innerpermission : childredPermissons) {
            map.put(innerpermission.getId(), innerpermission);
            if (permissionIds.contains(innerpermission.getId())){
                innerpermission.setChecked(true);
            }
        }

        for (Permission permission : childredPermissons) {
            //通过子查找父
            //子菜单
            Permission child = permission ; //假设为子菜单
            if(child.getPid() == null ){
                //默认展开父节点
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
    public boolean save(Role role) {
        boolean isSuccess = roleService.save(role);
        return isSuccess;
    }
    @ResponseBody
    @RequestMapping("/update")
    public boolean update(Role role) {
        boolean isSuccess = roleService.update(role);
        return isSuccess;
    }
    @ResponseBody
    @RequestMapping("/delete")
    public boolean delete(Integer id) {
        boolean isSuccess = roleService.deleteById(id);
        return isSuccess;
    }

    @ResponseBody
    @RequestMapping("/assignPermission")
    public boolean assignPermission(Integer roleid,Integer[] permissionid) {
        boolean isSuccess = roleService.assignPermission(roleid,permissionid);
        return isSuccess;
    }
}
