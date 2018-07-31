package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainRoleRequest;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.mapper.AccountRoleRelationMapper;
import com.todaysoft.ghealth.mybatis.mapper.AgencyRoleAuthorityMapper;
import com.todaysoft.ghealth.mybatis.mapper.AgencyRoleMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountRole;
import com.todaysoft.ghealth.mybatis.model.AgencyRole;
import com.todaysoft.ghealth.mybatis.model.AgencyRoleAuthority;
import com.todaysoft.ghealth.mybatis.searcher.AgencyRoleSearcher;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IAgcyRoleService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;
@Service
public class AgcyRoleService implements IAgcyRoleService {
    @Autowired
    private AgencyRoleMapper mapper;

    @Autowired
    private AgencyRoleAuthorityMapper agencyRoleAuthorityMapper;

    @Autowired
    private AccountRoleRelationMapper accountRoleRelationMapper;

    @Override
    public List<AgencyRole> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof AgencyRoleSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            AgencyRoleSearcher tis = (AgencyRoleSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        return mapper.search((AgencyRoleSearcher)searcher);
    }

    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof AgencyRoleSearcher))
        {
            throw new IllegalArgumentException();
        }
        return mapper.count((AgencyRoleSearcher)searcher);
    }

    @Override
    public List<AgencyRole> search(AgencyRoleSearcher searcher)
    {
        return mapper.search(searcher);
    }


    @Override
    public AgencyRole get(String id)
    {
        return mapper.get(id);
    }

    @Override
    @Transactional
    public void create(AgencyRole data)
    {
        mapper.insert(data);
    }

    @Override
    @Transactional
    public void insertRoleAuthority(AgencyRoleAuthority data)
    {
        agencyRoleAuthorityMapper.insert(data);
    }


    @Override
    public List<Authority> getRoleAuthorities(RoleAuthoritySearcher searcher)
    {
        return agencyRoleAuthorityMapper.search(searcher);
    }

    @Override
    @Transactional
    public void modify(AgencyRole data)
    {
        mapper.update(data);
    }

    @Override
    @Transactional
    public void deleteRoleAuthority(String roleId)
    {
        agencyRoleAuthorityMapper.deleteRoleAuthority(roleId);
    }

    @Override
    public int countById(String id)
    {
        return accountRoleRelationMapper.countByRoleId(id);
    }

    @Override
    @Transactional
    public void deleteAccountRole(String roleId)
    {
        accountRoleRelationMapper.deleteAccountRoleByRoleId(roleId);
    }

    @Override
    @Transactional
    public void insertAccountRole(AgencyAccountRole data)
    {
        accountRoleRelationMapper.insertAccountRole(data);
    }


    @Override
    public boolean isNameUnique(MaintainRoleRequest request)
    {
        AgencyRoleSearcher searcher = new AgencyRoleSearcher();
        searcher.setAgencyId(request.getAgencyId());
        searcher.setName(request.getName());
        searcher.setNameExactMatches(true);
        if (!StringUtils.isEmpty(request.getId()))
        {
            searcher.setExcludeKeys(Collections.singleton(request.getId()));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }

    @Override
    public List<AgencyAccount> getAgencyAccount(UserRoleSearcher searcher)
    {
        return accountRoleRelationMapper.getAccount(searcher);
    }

}
