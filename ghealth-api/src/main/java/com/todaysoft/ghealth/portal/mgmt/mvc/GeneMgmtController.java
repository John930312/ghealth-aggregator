package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Gene;
import com.todaysoft.ghealth.base.response.model.GeneDetails;
import com.todaysoft.ghealth.mgmt.request.MaintainGeneRequest;
import com.todaysoft.ghealth.mgmt.request.QueryGenesRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.GeneMgmtFacade;

@RestController
@RequestMapping("/mgmt/gene")
public class GeneMgmtController
{
    @Autowired
    private GeneMgmtFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Gene> pager(@RequestBody QueryGenesRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/details")
    public ObjectResponse<GeneDetails> details(@RequestBody QueryDetailsRequest request)
    {
        return new ObjectResponse<GeneDetails>(facade.get(request));
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainGeneRequest request)
    {
        facade.create(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainGeneRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainGeneRequest request)
    {
        facade.delete(request);
    }

    @RequestMapping("/unique/name")
    public ObjectResponse<Boolean> isNameUnique(@RequestBody MaintainGeneRequest request)
    {
        return facade.isNameUnique(request);
    }

    @RequestMapping("/unique/symbol")
    public ObjectResponse<Boolean> isSymbolUnique(@RequestBody MaintainGeneRequest request)
    {
        return facade.isSymbolUnique(request);
    }
}
