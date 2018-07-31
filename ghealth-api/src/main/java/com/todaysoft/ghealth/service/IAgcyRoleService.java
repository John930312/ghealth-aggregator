package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.agcy.request.MaintainRoleRequest;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountRole;
import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.model.AgencyRoleAuthority;
import com.todaysoft.ghealth.mybatis.searcher.AgencyRoleSearcher;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IAgcyRoleService extends PagerQueryHandler<AgencyRole> {

    List<AgencyRole> search(AgencyRoleSearcher searcher);

    AgencyRole get(String id);

    void create(AgencyRole data);

    void insertRoleAuthority(AgencyRoleAuthority data);

    List<Authority> getRoleAuthorities(RoleAuthoritySearcher searcher);

    void modify(AgencyRole data);

    void deleteRoleAuthority(String roleId);

    void deleteAccountRole(String roleId);

    void insertAccountRole(AgencyAccountRole data);

    int countById(String id);

    boolean isNameUnique(MaintainRoleRequest request);

    List<AgencyAccount> getAgencyAccount(UserRoleSearcher searcher);
}
