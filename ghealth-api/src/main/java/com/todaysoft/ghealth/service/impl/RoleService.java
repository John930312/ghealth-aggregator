package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.mybatis.mapper.RoleAuthorityMapper;
import com.todaysoft.ghealth.mybatis.mapper.RoleMapper;
import com.todaysoft.ghealth.mybatis.mapper.UserRoleMapper;
import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.model.RoleAuthority;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserRole;
import com.todaysoft.ghealth.mybatis.searcher.RoleAuthoritySearcher;
import com.todaysoft.ghealth.mybatis.searcher.RoleSearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.service.IRoleService;

@Service
public class RoleService implements IRoleService
{
    
    @Autowired
    private RoleMapper mapper;
    
    @Autowired
    private RoleAuthorityMapper roleAuthorityMapper;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Override
    public List<Role> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof RoleSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            RoleSearcher tis = (RoleSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        return mapper.search((RoleSearcher)searcher);
    }
    
    @Override
    public List<Role> search(RoleSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    @Transactional
    public void create(Role data)
    {
        mapper.insert(data);
    }
    
    @Override
    @Transactional
    public void modify(Role data)
    {
        mapper.update(data);
    }
    
    @Override
    public Role get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        mapper.delete(id);
    }
    
    @Override
    @Transactional
    public void insertRoleAuthority(RoleAuthority data)
    {
        roleAuthorityMapper.insert(data);
    }
    
    @Override
    public List<Authority> getRoleAuthorities(RoleAuthoritySearcher searcher)
    {
        return roleAuthorityMapper.search(searcher);
    }
    
    @Override
    @Transactional
    public void deleteRoleAuthority(String roleId)
    {
        roleAuthorityMapper.deleteRoleAuthority(roleId);
    }
    
    @Override
    @Transactional
    public void deleteUserRole(String roleId)
    {
        userRoleMapper.deleteUserRoleByRoleId(roleId);
    }
    
    @Override
    @Transactional
    public void insertUserRole(UserRole data)
    {
        userRoleMapper.insert(data);
    }
    
    @Override
    public List<User> getUsers(UserRoleSearcher searcher)
    {
        return userRoleMapper.getUsers(searcher);
    }

    @Override
    public int countById(String id)
    {
        return userRoleMapper.countByRoleId(id);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof RoleSearcher))
        {
            throw new IllegalArgumentException();
        }
        return mapper.count((RoleSearcher)searcher);
    }

    @Override
    public boolean isNameUnique(String id, String name)
    {
        RoleSearcher searcher = new RoleSearcher();
        searcher.setName(name);
        searcher.setNameExactMatches(true);
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }
}
