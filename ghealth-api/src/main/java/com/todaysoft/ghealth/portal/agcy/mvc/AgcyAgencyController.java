package com.todaysoft.ghealth.portal.agcy.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.model.Agency;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyRequest;
import com.todaysoft.ghealth.portal.agcy.facade.AgencyAgcyFacade;

@RestController
@RequestMapping("/agcy/agency")
public class AgcyAgencyController
{
    @Autowired
    private AgencyAgcyFacade facade;
    
    @RequestMapping("/get")
    public ObjectResponse<Agency> get(@RequestBody MaintainAgencyRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainAgencyRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/changePassword")
    public void changePassword(@RequestBody MaintainAgencyRequest request)
    {
        facade.changePassword(request);
    }
}
