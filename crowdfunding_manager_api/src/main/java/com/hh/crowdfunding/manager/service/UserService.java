package com.hh.crowdfunding.manager.service;

import com.hh.crowdfunding.domain.Role;
import com.hh.crowdfunding.domain.User;
import com.hh.crowdfunding.exception.LoginFailException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * @author hh
 * @create 2019-09-10 13:52
 */
public interface UserService {
    /**
     * 管理员登录
     *
     * @param loginInfo
     * @return
     */
    User login(Map<String, Object> loginInfo) throws LoginFailException;

    /**
     * 分页查询所有数据
     *
     * @param page
     * @param size
     * @return
     */
    List<User> findAll(int page, int size);

    /**
     * 根据username或email查询
     * @param page
     * @param size
     * @param condition
     * @param cond
     * @return
     */
    List<User> findByUsernameOrEmail(int page, int size,String condition,String cond);

    /**
     * 保存用户，成功返回true，失败返回false
     * @param user
     * @return
     */
    boolean save(User user);

    /**
     * 按id查询
     * @param id
     */
    User findById(Integer id);

    /**
     * 更新用户
     * @param user
     * @return
     */
    boolean update(User user);

    /**
     * 根据id删除用户
     * @param id
     * @return
     */
    boolean deleteById(Integer id);

    /**
     * 根据uid查多个角色id进而查角色
     * @param uid
     * @return
     */
    List<Role> findByRoleIds(Integer uid);
    /**
     * 根据uid查多个角色id进而查没有的角色
     * @param uid
     * @return
     */
    List<Role> findNoRoleByRoleIds(Integer uid);

    /**
     * 分配角色
     * @param roleIds
     * @param userId
     * @return
     */
    boolean assignRole(Integer[] roleIds, Integer userId);
}
