package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.base.response.model.Authority;

public interface AuthorityMapper
{
    int delete(String id);
    
    int insert(Authority record);
    
    Authority get(String id);
    
    int update(Authority record);
    
    List<Authority> list();
    
    List<Authority> getAuthoritiesByParentId(String id);
    
    List<Authority> getParentAuthorities();
    
}