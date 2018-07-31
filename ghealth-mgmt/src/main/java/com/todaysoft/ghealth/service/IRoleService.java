package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.model.role.Role;
import com.todaysoft.ghealth.model.role.RoleSearcher;
import com.todaysoft.ghealth.model.user.User;
import com.todaysoft.ghealth.support.Pagination;

public interface IRoleService
{
    Pagination<Role> pagination(RoleSearcher searcher, int pageNo, int pageSize);
    
    List<Role> list(RoleSearcher searcher);
    
    void create(Role data);
    
    void modify(Role data);
    
    Role get(Role data);

    boolean delete(String id);
    
    List<AuthorityNode> getAuthorityNodes();
    
    void addUser(Role data);
    
    List<User> removeRepaeted(List<User> sources, List<User> targers);
    
    boolean isNameUnique(String id,String name);
}
