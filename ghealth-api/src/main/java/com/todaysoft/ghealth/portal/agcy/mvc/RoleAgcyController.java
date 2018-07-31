package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.agcy.request.MaintainRoleRequest;
import com.todaysoft.ghealth.agcy.request.QueryRoleRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyRole;
import com.todaysoft.ghealth.portal.agcy.facade.RoleAgcyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agcy/role")
public class RoleAgcyController {
    @Autowired
    private RoleAgcyFacade facade;

    @RequestMapping("/pager")
    public PagerResponse<AgencyRole> pager(@RequestBody QueryRoleRequest request)
    {
        return facade.pager(request);
    }

    @RequestMapping("/get")
    public ObjectResponse<AgencyRole> get(@RequestBody MaintainRoleRequest request)
    {
        return facade.get(request);
    }

    @RequestMapping("/create")
    public void create(@RequestBody MaintainRoleRequest request)
    {
        facade.create(request);
    }

    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainRoleRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/delete")
    public ObjectResponse<Boolean> delete(@RequestBody MaintainRoleRequest request)
    {
        return facade.delete(request);
    }

    @RequestMapping("/addUser")
    public void addUser(@RequestBody MaintainRoleRequest request)
    {
        facade.addUser(request);
    }

    @RequestMapping("/isNameUnique")
    public ObjectResponse<Boolean> isNameUnique(@RequestBody MaintainRoleRequest request)
    {
        return facade.isNameUnique(request);
    }
}
