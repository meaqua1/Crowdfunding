package com.hh.crowdfunding.manager.service.impl;

import com.github.pagehelper.PageHelper;
import com.hh.crowdfunding.domain.Role;
import com.hh.crowdfunding.domain.User;
import com.hh.crowdfunding.exception.LoginFailException;
import com.hh.crowdfunding.manager.dao.RoleMapper;
import com.hh.crowdfunding.manager.dao.UserMapper;
import com.hh.crowdfunding.manager.dao.UserRoleMapper;
import com.hh.crowdfunding.manager.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author hh
 * @create 2019-09-09 15:31
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    /**
     * 管理员登录
     *
     * @param loginInfo
     * @return
     */
    @Override
    public User login(Map<String, Object> loginInfo) throws LoginFailException {
        User user = null;
        try {
            user = userMapper.selectByLoginacctAndUserpswd(loginInfo);
            if (user == null) {
                throw new LoginFailException("用户名或密码不正确");
            }
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
            throw new LoginFailException("登录失败");
        }
    }

    /**
     * 分页查询所有数据
     *
     * @param page
     * @param size
     * @return
     */
    @Override
    public List<User> findAll(int page, int size) {
        PageHelper.startPage(page, size);
        List<User> users = userMapper.selectAll();
        return users;
    }

    /**
     * 根据username或email查询
     *
     * @param page
     * @param size
     * @param condition
     * @return
     */
    @Override
    public List<User> findByUsernameOrEmail(int page, int size, String condition, String cond) {
        if (condition.contains("%")){
            condition=condition.replaceAll("%","\\\\%");
        }
        List<User> users = null;
        switch (cond) {
            case "username":
                PageHelper.startPage(page, size);
                try {
                    users = userMapper.findByUsername(condition);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
            case "email":
                PageHelper.startPage(page, size);
                try {
                    users = userMapper.findByEmail(condition);
                } catch (SQLException e) {
                    e.printStackTrace();
                }
                break;
        }
        return users;
    }
    @Override
    public boolean save(User user) {
        User u = userMapper.findByLoginacct(user.getLoginacct());
        if (u!=null){
            return false;
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            user.setCreatetime(sdf.format(new Date()));
            userMapper.insert(user);
            return true;
        }
    }

    /**
     * 按id查询
     *
     * @param id
     */
    @Override
    @Transactional
    public User findById(Integer id) {
        userMapper.selectByPrimaryKey(id);
        return userMapper.selectByPrimaryKey(id);
    }
/*    @Override
    @Transactional(isolation = Isolation.SERIALIZABLE)
    public User findById(Integer id) {
        System.out.println(userMapper.selectByPrimaryKey(id));
        try {
            Thread.sleep(1000000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return userMapper.selectByPrimaryKey(id);
    }*/
    /**
     * 更新用户
     *
     * @param user
     * @return
     */
    @Override
    public boolean update(User user) {
        int i = userMapper.updateByPrimaryKey(user);
        if (i==0){
            return false;
        }else {
            return true;
        }
    }

    /**
     * 根据id删除用户
     *
     * @param id
     * @return
     */
    @Override
    public boolean deleteById(Integer id) {
        int i = userMapper.deleteByPrimaryKey(id);
        if (i==0){
            return false;
        }else {
            return true;
        }

    }

    /**
     * 根据uid查多个角色id进而查角色
     *
     * @param uid
     * @return
     */
    @Override
    public List<Role> findByRoleIds(Integer uid) {
        List<Integer> roleIds = userMapper.findRoleIdByUserId(uid);
        if (!roleIds.isEmpty()) {
            List<Role> roles = roleMapper.findByRoleIds(roleIds);
            return roles;
        }else {
            return null;
        }

    }

    /**
     * 根据uid查多个角色id进而查没有的角色
     *
     * @param uid
     * @return
     */
    @Override
    public List<Role> findNoRoleByRoleIds(Integer uid) {
        List<Integer> roleIds = userMapper.findRoleIdByUserId(uid);
        List<Role> roles = roleMapper.selectAll();
        if (roleIds.isEmpty()){
            return roles;
        }else {
            List<Role> containsRoles = roleMapper.findByRoleIds(roleIds);
            for (Role containsRole : containsRoles) {
                roles.remove(containsRole);
            }
            return roles;
        }
    }

    /**
     * 分配角色
     *
     * @param roleIds
     * @param userId
     * @return
     */
    @Override
    public boolean assignRole(Integer[] roleIds, Integer userId) {
        if (roleIds[0]==0){
            int i1 = userRoleMapper.deleteByuserId(userId);
            return true;
        }else {
            int i1 = userRoleMapper.deleteByuserId(userId);
            int i = userRoleMapper.save(userId, roleIds);
            if (i==1&&i1==1){
                return true;
            }else {
                return false;
            }
        }
    }
}
