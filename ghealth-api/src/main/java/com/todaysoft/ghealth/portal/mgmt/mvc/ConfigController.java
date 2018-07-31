package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.mgmt.request.MaintainConfigRequest;
import com.todaysoft.ghealth.mgmt.request.QueryConfigRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.ConfigFacade;

@RestController
@RequestMapping("/mgmt/config")
public class ConfigController
{
    @Autowired
    private ConfigFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Config> pager(@RequestBody QueryConfigRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainConfigRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/get")
    public ObjectResponse<Config> get(@RequestBody MaintainConfigRequest request)
    {
        return facade.get(request);
    }

    @RequestMapping("list")
    public ListResponse<Config> list(@RequestBody QueryConfigRequest request)
    {
        return facade.list(request);
    }
}
