package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.model.AgencyProductOldPrice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.mgmt.request.MaintainProductAgencyRequest;
import com.todaysoft.ghealth.mgmt.request.QueryProductAgencyRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.ProductMgmtFacade;

import java.util.List;

@RestController
@RequestMapping("/mgmt/products-agency")
public class ProductMgmtController
{
    
    @Autowired
    private ProductMgmtFacade facsde;
    
    @RequestMapping("/pager")
    public PagerResponse<AgencyProduct> pager(@RequestBody QueryProductAgencyRequest request)
    {
        return facsde.pager(request);
    }
    
    @RequestMapping("/getProductAgentsByProductId")
    public ListResponse<AgencyProduct> getProductAgentsByProductId(@RequestBody QueryProductAgencyRequest request)
    {
        return facsde.getProductAgentsByProductId(request);
    }
    
    @RequestMapping("/allocate")
    public void allocate(@RequestBody MaintainProductAgencyRequest request)
    {
        facsde.allocate(request);
    }

    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainProductAgencyRequest request)
    {
        facsde.modify(request);
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainProductAgencyRequest request)
    {
        facsde.delete(request);
    }

    @RequestMapping("/addPro")
    public void addPro(@RequestBody MaintainProductAgencyRequest request)
    {
        facsde.addPro(request);
    }

    @RequestMapping("/deletePro")
    public void deletePro(@RequestBody MaintainProductAgencyRequest request)
    {
        facsde.deletePro(request);
    }

    @RequestMapping("/getOldPriceDetails")
    public ListResponse<AgencyProductOldPrice> getOldPriceDetails(@RequestBody QueryProductAgencyRequest request)
    {
        return facsde.getOldPriceDetails(request);
    }
}
