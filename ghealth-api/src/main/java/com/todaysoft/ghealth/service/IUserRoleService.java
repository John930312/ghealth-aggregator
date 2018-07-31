package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.UserRoleRelation;

public interface IUserRoleService
{
    void create(UserRoleRelation data);
    
    void modify(UserRoleRelation data);
    
    void deleteByroleId(String roleId);
    
    void deleteByuserId(String userId);
}
