package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountRole;
import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.model.UserRoleRelation;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;

import java.util.List;

public interface AccountRoleRelationMapper {
    int countByRoleId(String id);

    void deleteAccountRoleByRoleId(String roleId);

    void deleteAccountRoleByUserId(String roleId);

    int insert(UserRoleRelation userRoleRelation);

    int update(UserRoleRelation userRoleRelation);

    List<AgencyRole> getRoles(UserRoleSearcher searcher);

    int insertAccountRole(AgencyAccountRole record);

    List<AgencyAccount> getAccount(UserRoleSearcher searcher);
}
