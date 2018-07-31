package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.agcy.request.MaintainAccountRequest;
import com.todaysoft.ghealth.agcy.request.QueryAccountRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyAccount;
import com.todaysoft.ghealth.portal.agcy.facade.AccountAgcyFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agcy/agencyAccount")
public class AccountAgcyController {
    @Autowired
    private AccountAgcyFacade facade;

    @RequestMapping("/pager")
    public PagerResponse<AgencyAccount> pager(@RequestBody QueryAccountRequest request)
    {
        return facade.pager(request);
    }
    @RequestMapping("/list")
    public ListResponse<AgencyAccount> list(@RequestBody QueryAccountRequest request)
    {
        return facade.list(request);
    }

    @RequestMapping("/create")
    public void create(@RequestBody MaintainAccountRequest request)
    {
        facade.create(request);
    }

    @RequestMapping("/get")
    public ObjectResponse<AgencyAccount> get(@RequestBody MaintainAccountRequest request)
    {
        return facade.get(request);
    }

    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainAccountRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/change")
    public void change(@RequestBody MaintainAccountRequest request)
    {
        facade.change(request);
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainAccountRequest request)
    {
        facade.delete(request);
    }

    @RequestMapping("/unique/name")
    public ObjectResponse<Boolean> isNameUnique(@RequestBody MaintainAccountRequest request)
    {
        return facade.isNameUnique(request);
    }
}
