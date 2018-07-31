package com.todaysoft.ghealth.portal.agcy.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.agcy.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyBill;
import com.todaysoft.ghealth.portal.agcy.facade.AgcyAgencyBillFacade;

@RestController
@RequestMapping("/agcy/agencyBill")
public class AgcyAgencyBillController
{
    @Autowired
    private AgcyAgencyBillFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<AgencyBill> pager(@RequestBody QueryAgencyBillRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/list")
    public ListResponse<AgencyBill> list(@RequestBody QueryAgencyBillRequest request)
    {
        return facade.list(request);
    }
}
