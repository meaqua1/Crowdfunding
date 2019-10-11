package com.hh.crowdfunding.manager.service;

import com.hh.crowdfunding.domain.Role;

import java.util.List;

/**
 * @author hh
 * @create 2019-09-17 14:18
 */
public interface RoleService {

    List<Role> findAll();

    boolean save(Role role);

    Role findById(Integer id);

    boolean update(Role role);

    boolean deleteById(Integer id);

    boolean assignPermission(Integer roleid, Integer[] permissionid);
}
