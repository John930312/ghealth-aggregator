package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.agcy.request.QueryConfigRequest;
import com.todaysoft.ghealth.agcy.request.QueryOrdersRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.portal.agcy.facade.AgcyConfigFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agcy/config")
public class AgcyConfigController
{
    @Autowired
    private AgcyConfigFacade facade;

    @RequestMapping("list")
    public ListResponse<Config> list(@RequestBody QueryConfigRequest request)
    {
        return facade.list(request);
    }
}
