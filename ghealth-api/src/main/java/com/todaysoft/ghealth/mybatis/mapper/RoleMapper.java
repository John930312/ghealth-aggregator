package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.searcher.RoleSearcher;

public interface RoleMapper
{
    int delete(String id);
    
    int insert(Role record);
    
    Role get(String id);
    
    int update(Role record);
    
    int count(RoleSearcher searcher);
    
    List<Role> search(RoleSearcher searcher);
    
}