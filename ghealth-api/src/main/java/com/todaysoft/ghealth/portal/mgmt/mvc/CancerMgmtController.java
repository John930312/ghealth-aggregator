package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Cancer;
import com.todaysoft.ghealth.mgmt.request.MaintainCancerRequest;
import com.todaysoft.ghealth.mgmt.request.QueryCancersRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.CancerMgmtFacade;

@RestController
@RequestMapping("/mgmt/cancer")
public class CancerMgmtController
{
    @Autowired
    private CancerMgmtFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Cancer> pager(@RequestBody QueryCancersRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/details")
    public ObjectResponse<Cancer> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainCancerRequest request)
    {
        facade.delete(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainCancerRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainCancerRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/unique/name")
    public ObjectResponse<Boolean> isNameUnique(@RequestBody MaintainCancerRequest request)
    {
        return facade.isNameUnique(request);
    }
}
