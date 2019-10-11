package com.hh.crowdfunding.manager.service.impl;

import com.hh.crowdfunding.domain.Role;
import com.hh.crowdfunding.manager.dao.RoleMapper;
import com.hh.crowdfunding.manager.dao.RolePermissionMapper;
import com.hh.crowdfunding.manager.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-17 14:18
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Override
    public List<Role> findAll() {
        return roleMapper.selectAll();
    }

    @Override
    public boolean save(Role role) {
        int i = roleMapper.insert(role);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public Role findById(Integer id) {
        return roleMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Role role) {
        int i = roleMapper.updateByPrimaryKey(role);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean deleteById(Integer id) {
        int i = roleMapper.deleteByPrimaryKey(id);
        if (i == 1){
            return true;
        }else {
            return false;
        }
    }

    @Override
    public boolean assignPermission(Integer roleid, Integer[] permissionid) {
        if (permissionid == null) {
            rolePermissionMapper.deleteByRoleId(roleid);
            return true;
        }else {
            rolePermissionMapper.deleteByRoleId(roleid);
            int i = rolePermissionMapper.savePermissionIdByRoleId(roleid,permissionid);
            if (i != 0){
                return true;
            }else {
                return false;
            }
        }
    }
}
