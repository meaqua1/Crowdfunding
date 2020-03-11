package com.hh.crowdfunding_main;

import com.hh.crowdfunding.manager.dao.RoleMapper;
import com.hh.crowdfunding.manager.dao.UserMapper;
import com.hh.crowdfunding.manager.dao.UserRoleMapper;
import com.hh.crowdfunding.manager.service.PermissionService;
import com.hh.crowdfunding.manager.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CrTests {
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private PermissionService permissionService;


    @Test
    public void contextLoads(){
        userService.findById(1);
    }
    @Test
    public void Test(){
        userService.findById(1);

    }

}