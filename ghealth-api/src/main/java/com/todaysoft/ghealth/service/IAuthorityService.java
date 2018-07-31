package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IAuthorityService extends PagerQueryHandler<Authority>
{
    @Override
    List<Authority> query(Object searcher, int offset, int limit);
    
    @Override
    int count(Object searcher);
    
    List<Authority> list();
    
    List<Authority> getAuthoritiesByParentId(String id);
    
    List<Authority> getParentAuthorities();
}
