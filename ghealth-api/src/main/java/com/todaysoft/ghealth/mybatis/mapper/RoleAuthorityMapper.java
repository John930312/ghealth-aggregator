package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.model.RoleAuthority;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;

public interface RoleAuthorityMapper
{
    int insert(RoleAuthority record);
    
    List<Authority> search(RoleAuthoritySearcher searcher);
    
    void deleteRoleAuthority(String roleId);
}