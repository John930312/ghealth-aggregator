package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.model.RoleAuthority;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserRole;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;
import com.todaysoft.ghealth.mybatis.searcher.RoleSearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IRoleService extends PagerQueryHandler<Role>
{
    List<Role> search(RoleSearcher searcher);
    
    void create(Role data);
    
    void modify(Role data);
    
    Role get(String id);
    
    void delete(String id);
    
    void insertRoleAuthority(RoleAuthority data);
    
    List<Authority> getRoleAuthorities(RoleAuthoritySearcher searcher);
    
    void deleteRoleAuthority(String roleId);
    
    void deleteUserRole(String roleId);
    
    void insertUserRole(UserRole data);
    
    List<User> getUsers(UserRoleSearcher searcher);

    int countById(String id);

    boolean isNameUnique(String id, String name);
}
