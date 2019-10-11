package com.hh.crowdfunding.manager.service.impl;

import com.hh.crowdfunding.domain.Permission;
import com.hh.crowdfunding.manager.dao.PermissionMapper;
import com.hh.crowdfunding.manager.dao.RolePermissionMapper;
import com.hh.crowdfunding.manager.dao.UserRoleMapper;
import com.hh.crowdfunding.manager.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-15 16:05
 */
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionMapper permissionMapper;
    @Autowired
    private RolePermissionMapper rolePermissionMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Override
    public List<Permission> queryAllPermission() {
        return permissionMapper.selectAll();
    }
    @Override
    public boolean save(Permission permission) {
        int i = permissionMapper.insert(permission);
        if (i==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public Permission findById(Integer id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public boolean update(Permission permission) {
        int i = permissionMapper.updateById(permission);
        if (i==0){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public boolean delete(Integer id) {
        int i = permissionMapper.deleteByPrimaryKey(id);
        if (i==0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 根据roleId查t_role_permission中间表得到PermissionID
     * @param roleId
     * @return
     */
    @Override
    public List<Integer> findByRoleId(Integer roleId) {
        return rolePermissionMapper.findByRoleId(roleId);
    }

    /**
     * 根据userid查roleid，根据roleid查PermissionID
     *
     * @param userId
     * @return
     */
    @Override
    public List<Permission> findByUserId(Integer userId) {
        return userRoleMapper.findRoleIdByUserId(userId);
    }
}
