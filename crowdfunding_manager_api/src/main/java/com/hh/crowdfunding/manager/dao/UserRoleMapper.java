package com.hh.crowdfunding.manager.dao;

import com.hh.crowdfunding.domain.Permission;
import com.hh.crowdfunding.domain.UserRole;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserRoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserRole record);

    UserRole selectByPrimaryKey(Integer id);

    List<UserRole> selectAll();

    int updateByPrimaryKey(UserRole record);
    //根据uid删除所有角色
    int deleteByuserId(Integer userId);

    int save(@Param("userId") Integer userId, @Param("roleIds") Integer[] roleIds);

    List<Permission> findRoleIdByUserId(Integer userId);
}