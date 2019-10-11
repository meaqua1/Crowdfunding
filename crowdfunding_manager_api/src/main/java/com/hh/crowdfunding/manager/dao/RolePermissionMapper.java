package com.hh.crowdfunding.manager.dao;

import com.hh.crowdfunding.domain.RolePermission;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface RolePermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(RolePermission record);

    RolePermission selectByPrimaryKey(Integer id);

    List<RolePermission> selectAll();

    int updateByPrimaryKey(RolePermission record);

    List<Integer> findByRoleId(Integer roleId);

    int deleteByRoleId(Integer roleid);

    int savePermissionIdByRoleId(@Param("roleid") Integer roleid, @Param("permissionids")Integer[] permissionids);
}