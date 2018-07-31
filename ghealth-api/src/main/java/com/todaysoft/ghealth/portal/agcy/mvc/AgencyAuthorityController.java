package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Authority;
import com.todaysoft.ghealth.base.response.model.AuthorityNode;
import com.todaysoft.ghealth.portal.agcy.facade.AgencyAuthorityFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agcy/authority")
public class AgencyAuthorityController {
    @Autowired
    private AgencyAuthorityFacade facade;

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
