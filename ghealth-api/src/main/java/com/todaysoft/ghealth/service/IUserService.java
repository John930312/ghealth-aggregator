package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;
import com.todaysoft.ghealth.service.model.UserAccountLoginDetails;

public interface IUserService extends PagerQueryHandler<User>
{
    void create(User data);
    
    User get(String id);
    
    void modify(User data);
    
    List<User> list(UserSearcher searcher);
    
    UserAccountLoginDetails login(String username, String password);
    
    List<Role> getRoles(UserRoleSearcher searcher);
    
    boolean isUsernameUnique(String username,String id);
}
