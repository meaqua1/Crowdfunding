package com.hh.crowdfunding.manager.dao;

import com.hh.crowdfunding.domain.Permission;
import java.util.List;

public interface PermissionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Permission record);

    Permission selectByPrimaryKey(Integer id);

    List<Permission> selectAll();

    int updateByPrimaryKey(Permission record);
    //动态修改，若数据为空则不变
    int updateById(Permission record);
}