package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.mgmt.request.QueryAreaRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.AreaMgmtFacade;

@RestController
@RequestMapping("/mgmt/area")
public class AreaMgmtController
{
    @Autowired
    private AreaMgmtFacade facade;
    
    @RequestMapping("/findProvince")
    private ListResponse<Area> list(@RequestBody QueryAreaRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping("/findByParentId")
    private ListResponse<Area> list1(@RequestBody QueryAreaRequest request)
    {
        return facade.list(request);
    }
    
}
