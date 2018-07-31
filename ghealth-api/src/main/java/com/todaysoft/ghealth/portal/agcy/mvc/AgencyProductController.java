package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.agcy.request.QueryAgencyProductsRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyProduct;
import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.portal.agcy.facade.AgencyProductAgcyFacade;

@RestController
@RequestMapping("/agcy/agent-products")
public class AgencyProductController
{
    @Autowired
    private AgencyProductAgcyFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<AgencyProduct> pager(@RequestBody QueryAgencyProductsRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/details")
    public ObjectResponse<AgencyProductDetails> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.details(request);
    }
    
    @RequestMapping("/list")
    public ListResponse<AgencyProduct> list(@RequestBody QueryAgencyProductsRequest request)
    {
        return facade.list(request);
    }

    @RequestMapping("/isUniqueCode")
    public ObjectResponse<Boolean> isUniqueCode(@RequestBody QueryAgencyProductsRequest request)
    {
        return facade.isUniqueCode(request);
    }
}
