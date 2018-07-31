package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserAccountToken;
import com.todaysoft.ghealth.mybatis.searcher.UserSearcher;

public interface UserMapper
{
    int insert(User record);
    
    User get(String id);
    
    User getUserByUsername(String username);
    
    int modify(User record);
    
    List<User> search(UserSearcher searcher);
    
    int count(UserSearcher searcher);
    
    UserAccountToken getAccountTokenRecordByToken(String token);
    
    UserAccountToken getAccountTokenRecordByAccountId(String accountId);
    
    void insertAccountToken(UserAccountToken data);
    
    void updateAccountToken(UserAccountToken data);
    
    List<String> getUserAuthorizedResources(String id);
}