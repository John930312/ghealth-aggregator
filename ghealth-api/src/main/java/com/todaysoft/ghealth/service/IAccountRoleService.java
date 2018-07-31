package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.UserRoleRelation;

public interface IAccountRoleService {
    void create(UserRoleRelation data);

    void modify(UserRoleRelation data);

    void deleteAccountRoleByUserId(String userId);
}
