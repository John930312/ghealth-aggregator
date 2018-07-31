package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.portal.mgmt.facade.AuthorityFacade;

@RestController
@RequestMapping("/mgmt/authority")
public class AuthorityController
{
    @Autowired
    private AuthorityFacade facade;
    
    @RequestMapping("/getAuthorityNodes")
    public ListResponse<AuthorityNode> getAuthorityNodes()
    {
        return facade.getAuthorityNodes();
    }
    
    @RequestMapping("/list")
    public ListResponse<Authority> list()
    {
        return facade.list();
    }
}
