package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Role;
import com.todaysoft.ghealth.mgmt.request.MaintainRoleRequest;
import com.todaysoft.ghealth.mgmt.request.QueryRoleRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.RoleFacade;

@RestController
@RequestMapping("/mgmt/role")
public class RoleController
{
    @Autowired
    private RoleFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Role> pager(@RequestBody QueryRoleRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/list")
    public ListResponse<Role> list(@RequestBody QueryRoleRequest request)
    {
        return facade.list(request);
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
    
    @RequestMapping("/get")
    public ObjectResponse<Role> get(@RequestBody MaintainRoleRequest request)
    {
        return facade.get(request);
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
