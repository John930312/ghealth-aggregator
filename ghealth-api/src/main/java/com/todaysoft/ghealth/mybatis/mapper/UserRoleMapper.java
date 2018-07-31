package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserRole;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;

public interface UserRoleMapper
{
    int insert(UserRole record);
    
    void deleteUserRoleByRoleId(String roleId);
    
    List<User> getUsers(UserRoleSearcher searcher);
    
    List<Role> getRoles(UserRoleSearcher searcher);

    int countByRoleId(String id);
}