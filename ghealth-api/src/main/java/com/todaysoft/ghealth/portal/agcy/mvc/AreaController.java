package com.todaysoft.ghealth.portal.agcy.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.agcy.request.QueryAreaRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.portal.agcy.facade.AreaFacade;

@RestController
@RequestMapping("/agcy/area")
public class AreaController
{
    @Autowired
    private AreaFacade facade;
    
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
