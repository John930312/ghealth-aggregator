package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.TestingItemLocusDTO;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingItemLocusRequest;
import com.todaysoft.ghealth.mgmt.request.QueryItemLocusRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.ItemLocusFacade;

@RestController()
@RequestMapping("/mgmt/item-locus")
public class ItemLocusController
{
    @Autowired
    private ItemLocusFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<TestingItemLocusDTO> pager(@RequestBody QueryItemLocusRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/details")
    public ObjectResponse<TestingItemLocusDTO> get(@RequestBody MaintainTestingItemLocusRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainTestingItemLocusRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainTestingItemLocusRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainTestingItemLocusRequest request)
    {
        facade.delete(request);
    }
}
