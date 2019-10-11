package com.hh.crowdfunding.manager.dao;

import com.hh.crowdfunding.domain.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);
    //判断登录是否合法
    User selectByLoginacctAndUserpswd(Map<String,Object> loginInfo) throws SQLException;
    //根据username模糊查询
    List<User> findByUsername(String username) throws SQLException;
    //根据loginacct得到唯一User
    User findByLoginacct(String loginacct);
    //根据email模糊查询
    List<User> findByEmail(String email) throws SQLException;
    //根据uid查中间表得到roleid
    List<Integer> findRoleIdByUserId(Integer id);
}