package com.hh.crowdfunding.manager.service;

import com.hh.crowdfunding.domain.Permission;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-15 16:05
 */
public interface PermissionService {
    //查找所有
    List<Permission> queryAllPermission();

    boolean save(Permission permission);

    Permission findById(Integer id);

    boolean update(Permission permission);

    boolean delete(Integer id);

    /**
     * 根据roleId查t_role_permission中间表得到PermissionID
     * @param roleId
     * @return
     */
    List<Integer> findByRoleId(Integer roleId);

    /**
     * 根据userid查roleid，根据roleid查PermissionID
     * @param userId
     * @return
     */
    List<Permission> findByUserId(Integer userId);
}
