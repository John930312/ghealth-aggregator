package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.model.AgencyRoleAuthority;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;

import java.util.List;

public interface AgencyRoleAuthorityMapper {

    int insert(AgencyRoleAuthority record);

    List<Authority> search(RoleAuthoritySearcher searcher);

    void deleteRoleAuthority(String roleId);
}
