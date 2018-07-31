package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.service.IAuthorityService;

@Component
public class AuthorityFacade
{
    @Autowired
    private IAuthorityService authorityService;
    
    public ListResponse<Authority> list()
    {
        //所有的父节点
        List<Authority> authorities = authorityService.list();
        return new ListResponse<Authority>(authorities);
    }
    
    public ListResponse<AuthorityNode> getAuthorityNodes()
    {
        //所有的父节点
        List<Authority> authorities = authorityService.getParentAuthorities();
        List<AuthorityNode> AuthorityNodes = new ArrayList<AuthorityNode>();
        if (!CollectionUtils.isEmpty(authorities))
        {
            for (Authority authority : authorities)
            {
                AuthorityNodes.add(warp(authority));
            }
        }
        return new ListResponse<AuthorityNode>(AuthorityNodes);
    }
    
    private AuthorityNode warp(Authority data)
    {
        if (null != data)
        {
            List<Authority> sons = authorityService.getAuthoritiesByParentId(data.getId());
            List<AuthorityNode> sonNodes = new ArrayList<AuthorityNode>();
            AuthorityNode authorityNode = new AuthorityNode();
            BeanUtils.copyProperties(data, authorityNode);
            if (!CollectionUtils.isEmpty(sons))
            {
                for (Authority authority : sons)
                {
                    AuthorityNode childNode = new AuthorityNode();
                    BeanUtils.copyProperties(authority, childNode);
                    sonNodes.add(childNode);
                    List<Authority> grandsons = authorityService.getAuthoritiesByParentId(authority.getId());
                    List<AuthorityNode> grandsonNodes = new ArrayList<AuthorityNode>();
                    if (!CollectionUtils.isEmpty(grandsons))
                    {
                        for (Authority a : grandsons)
                        {
                            AuthorityNode grandsonNode = new AuthorityNode();
                            BeanUtils.copyProperties(a, grandsonNode);
                            grandsonNodes.add(grandsonNode);
                            warp(a);
                        }
                    }
                    childNode.setChild(grandsonNodes);
                }
            }
            authorityNode.setChild(sonNodes);
            return authorityNode;
        }
        return null;
    }
}
