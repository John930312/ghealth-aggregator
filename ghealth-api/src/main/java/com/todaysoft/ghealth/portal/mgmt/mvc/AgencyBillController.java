package com.todaysoft.ghealth.portal.mgmt.mvc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.AgencyBill;
import com.todaysoft.ghealth.mgmt.request.QueryAgencyBillRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.AgencyBillFacade;

@RestController()
@RequestMapping("/mgmt/agencyBill")
public class AgencyBillController
{
    @Autowired
    private AgencyBillFacade facade;
    
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
