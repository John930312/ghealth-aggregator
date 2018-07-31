package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainAccountRequest;
import com.todaysoft.ghealth.mybatis.mapper.AccountRoleRelationMapper;
import com.todaysoft.ghealth.mybatis.mapper.AgcyAccountMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.searcher.AgencyAccountSearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IAgencyAccountService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class AgencyAccountService implements IAgencyAccountService{

    @Autowired
    private AgcyAccountMapper mapper;

    @Autowired
    private AccountRoleRelationMapper accountRoleRelationMapper;

    @Override
    public List<AgencyAccount> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof AgencyAccountSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            AgencyAccountSearcher tis = (AgencyAccountSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        return mapper.search((AgencyAccountSearcher)searcher);
    }

    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof AgencyAccountSearcher))
        {
            throw new IllegalArgumentException();
        }
        return mapper.count((AgencyAccountSearcher)searcher);
    }

    @Override
    public List<AgencyAccount> search(AgencyAccountSearcher searcher)
    {
        return mapper.search(searcher);
    }

    @Override
    public List<AgencyAccount> list(AgencyAccountSearcher searcher)
    {
        return mapper.search(searcher);
    }

    @Override
    public void create(AgencyAccount data)
    {
        mapper.insert(data);
    }

    @Override
    public AgencyAccount get(String id)
    {
        return mapper.get(id);
    }

    @Override
    public List<AgencyRole> getRoles(UserRoleSearcher searcher)
    {
        return accountRoleRelationMapper.getRoles(searcher);
    }

    @Override
    public void modify(AgencyAccount data)
    {
        mapper.modify(data);
    }

    @Override
    public boolean isNameUnique(MaintainAccountRequest request)
    {
        AgencyAccountSearcher searcher = new AgencyAccountSearcher();
        searcher.setUsername(request.getUsername());
        searcher.setUsernameExactMatches(true);
        searcher.setAgencyId(request.getAgencyId());
        if (!StringUtils.isEmpty(request.getId()))
        {
            searcher.setExcludeKeys(Collections.singleton(request.getId()));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }

}
