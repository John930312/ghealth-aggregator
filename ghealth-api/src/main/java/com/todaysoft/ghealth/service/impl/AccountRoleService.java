package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.AccountRoleRelationMapper;
import com.todaysoft.ghealth.mybatis.model.UserRoleRelation;
import com.todaysoft.ghealth.service.IAccountRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountRoleService implements IAccountRoleService{
    @Autowired
    private AccountRoleRelationMapper mapper;

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
    public void deleteAccountRoleByUserId(String userId)
    {
        mapper.deleteAccountRoleByUserId(userId);
    }
}
