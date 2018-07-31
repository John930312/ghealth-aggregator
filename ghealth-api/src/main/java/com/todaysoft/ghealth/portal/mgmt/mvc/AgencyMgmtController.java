package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Agency;
import com.todaysoft.ghealth.base.response.model.AgencyDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainAgencyRequest;
import com.todaysoft.ghealth.mgmt.request.QueryAgenciesRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.AgencyMgmtFacade;

@RestController
@RequestMapping("/mgmt/agencies")
public class AgencyMgmtController
{
    @Autowired
    private AgencyMgmtFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Agency> pager(@RequestBody QueryAgenciesRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping(value = "/details")
    public ObjectResponse<AgencyDetails> getDetails(@RequestBody QueryDetailsRequest request)
    {
        return facade.getDetails(request);
    }
    
    @RequestMapping(value = "/unique/username")
    public ObjectResponse<Boolean> isUsernameUnique(@RequestBody MaintainAgencyRequest request)
    {
        return facade.isUsernameUnique(request);
    }
    
    @RequestMapping(value = "/unique/code")
    public ObjectResponse<Boolean> isCodeUnique(@RequestBody MaintainAgencyRequest request)
    {
        return facade.isCodeUnique(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainAgencyRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainAgencyRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public ObjectResponse<Boolean> delete(@RequestBody MaintainAgencyRequest request)
    {
        return facade.delete(request);
    }
    
    @RequestMapping("/list")
    public ListResponse<Agency> list(@RequestBody QueryAgenciesRequest request)
    {
        return facade.list(request);
    }

    @RequestMapping("/recharge")
    public void recharge(@RequestBody MaintainAgencyRequest request)
    {
        facade.recharge(request);
    }
}
