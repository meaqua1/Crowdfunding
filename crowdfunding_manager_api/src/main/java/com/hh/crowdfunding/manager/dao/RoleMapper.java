package com.hh.crowdfunding.manager.dao;

import com.hh.crowdfunding.domain.Role;
import java.util.List;

public interface RoleMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Role record);

    Role selectByPrimaryKey(Integer id);

    List<Role> selectAll();

    int updateByPrimaryKey(Role record);
    //根据多个id查多个角色
    List<Role> findByRoleIds(List<Integer> roleIds);

}