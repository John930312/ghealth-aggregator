package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IAgencyAuthorityService extends PagerQueryHandler<Authority> {
    @Override
    List<Authority> query(Object searcher, int offset, int limit);

    @Override
    int count(Object searcher);

    List<Authority> list();

    List<Authority> getAuthoritiesByParentId(String id);

    List<Authority> getParentAuthorities();
}
