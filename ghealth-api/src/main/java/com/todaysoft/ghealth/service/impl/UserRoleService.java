package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.UserRoleRelationMapper;
import com.todaysoft.ghealth.mybatis.model.UserRoleRelation;
import com.todaysoft.ghealth.service.IUserRoleService;

@Service
public class UserRoleService implements IUserRoleService
{
    @Autowired
    private UserRoleRelationMapper mapper;
    
    @Override
    @Transactional
    public void create(UserRoleRelation data)
    {
        mapper.insert(data);
    }
    
    @Override
    public void modify(UserRoleRelation data)
    {
        mapper.update(data);
    }
    
    @Override
    public void deleteByroleId(String roleId)
    {
        mapper.deleteByroleId(roleId);
    }
    
    @Override
    public void deleteByuserId(String userId)
    {
        mapper.deleteByuserId(userId);
    }
}
