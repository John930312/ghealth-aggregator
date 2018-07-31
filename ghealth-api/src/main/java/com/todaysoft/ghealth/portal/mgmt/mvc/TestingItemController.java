package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.TestingItem;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingItemRequest;
import com.todaysoft.ghealth.mgmt.request.QueryTestingItemsRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.TestingItemMgmtFacade;

@RestController
@RequestMapping("/mgmt/testing-items")
public class TestingItemController
{
    @Autowired
    private TestingItemMgmtFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<TestingItem> pager(@RequestBody QueryTestingItemsRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/list")
    public ListResponse<TestingItem> list(@RequestBody QueryTestingItemsRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping(value = "/details")
    public ObjectResponse<TestingItem> getDetails(@RequestBody QueryDetailsRequest request)
    {
        return new ObjectResponse<TestingItem>(facade.getDetails(request));
    }
    
    @RequestMapping("/unique/code")
    public ObjectResponse<Boolean> isCodeUnique(@RequestBody MaintainTestingItemRequest request)
    {
        return facade.isCodeUnique(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainTestingItemRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainTestingItemRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public ObjectResponse<Boolean> delete(@RequestBody MaintainTestingItemRequest request)
    {
       return facade.delete(request);
    }

    @RequestMapping("/setIsEnabled")
    public ObjectResponse<Boolean> setIsEnabled(@RequestBody MaintainTestingItemRequest request)
    {
        return facade.setIsEnabled(request);
    }
}
