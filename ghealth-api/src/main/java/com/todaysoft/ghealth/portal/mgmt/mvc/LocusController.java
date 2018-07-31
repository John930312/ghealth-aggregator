package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.LocusDTO;
import com.todaysoft.ghealth.mgmt.request.MaintainLocusRequest;
import com.todaysoft.ghealth.mgmt.request.QueryLocusByNamesRequest;
import com.todaysoft.ghealth.mgmt.request.QueryLocusRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.LocusFacade;

@RestController
@RequestMapping("/mgmt/locus")
public class LocusController
{
    @Autowired
    private LocusFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<LocusDTO> pager(@RequestBody QueryLocusRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/list/names")
    public ListResponse<LocusDTO> list(@RequestBody QueryLocusByNamesRequest request)
    {
        return facade.list(request);
    }
    
    @RequestMapping("/get")
    public ObjectResponse<LocusDTO> get(@RequestBody MaintainLocusRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainLocusRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainLocusRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainLocusRequest request)
    {
        facade.delete(request);
    }
    
    @RequestMapping("/unique/name")
    public ObjectResponse<Boolean> isNameUnique(@RequestBody MaintainLocusRequest request)
    {
        return facade.isNameUnique(request);
    }
}
