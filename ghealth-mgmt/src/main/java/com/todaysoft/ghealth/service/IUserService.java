package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.model.user.User;
import com.todaysoft.ghealth.model.user.UserForm;
import com.todaysoft.ghealth.model.user.UserSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IUserService
{
    Pagination<User> searcher(UserSearcher searcher, int pageNo, int pageSize);
    
    void create(UserForm data);
    
    void delete(User data);
    
    User get(User data);
    
    void modify(UserForm data);
    
    List<User> list(UserSearcher searcher);
    
    void change(User data);
    
    boolean isUsernameUnique(String username,String id);
}
