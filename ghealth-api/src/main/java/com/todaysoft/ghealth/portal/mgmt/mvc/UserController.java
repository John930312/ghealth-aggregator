package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.User;
import com.todaysoft.ghealth.mgmt.request.MaintainUserRequest;
import com.todaysoft.ghealth.mgmt.request.QueryUserRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.UserFacade;

@RestController
@RequestMapping("/mgmt/user")
public class UserController
{
    @Autowired
    private UserFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<User> pager(@RequestBody QueryUserRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/list")
    public ListResponse<User> list(@RequestBody QueryUserRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainUserRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainUserRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/get")
    public ObjectResponse<User> get(@RequestBody MaintainUserRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainUserRequest request)
    {
        facade.delete(request);
    }
    
    @RequestMapping("/change")
    public void change(@RequestBody MaintainUserRequest request)
    {
        facade.change(request);
    }
    
    @RequestMapping("/isUsernameUnique")
    public ObjectResponse<Boolean> isUsernameUnique(@RequestBody MaintainUserRequest request)
    {
        return facade.isUsernameUnique(request);
    }
    
}
