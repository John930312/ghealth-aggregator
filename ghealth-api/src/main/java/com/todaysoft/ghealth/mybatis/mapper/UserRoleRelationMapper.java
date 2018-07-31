package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.UserRoleRelation;

public interface UserRoleRelationMapper
{
    void insert(UserRoleRelation userRoleRelation);
    
    int update(UserRoleRelation record);
    
    void deleteByroleId(String roleId);
    
    void deleteByuserId(String userId);
}
