package com.todaysoft.ghealth.portal.mgmt.mvc;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.mgmt.request.MaintainSimpleRequest;
import com.todaysoft.ghealth.mgmt.request.QueryOrdersRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.SampleMgmtFacade;
import com.todaysoft.ghealth.service.orderEvent.OrderEventLog;

@RestController
@RequestMapping("/mgmt/sample")
public class SampleMgmtController
{
    @Autowired
    private SampleMgmtFacade facade;
    
    @RequestMapping("pager")
    public PagerResponse<Order> pager(@RequestBody QueryOrdersRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/modifyStatus")
    @OrderEventLog(eventType = "3",handler = "mgmt")
    public Map<String, String> modifyStatus(@RequestBody MaintainSimpleRequest request)
    {
        return facade.modifyStatus(request);
    }
    
    @RequestMapping("/delete")
    public ObjectResponse<Boolean> status(@RequestBody MaintainSimpleRequest request)
    {
        return facade.isStatus(request);
    }
    
    @RequestMapping("/getInformation")
    public ObjectResponse<Order> details(@RequestBody QueryOrdersRequest request)
    {
        return facade.getInformation(request);
    }
}
