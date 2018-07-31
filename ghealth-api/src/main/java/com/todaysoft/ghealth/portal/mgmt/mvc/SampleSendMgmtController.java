package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.mgmt.request.MaintainSimpleRequest;
import com.todaysoft.ghealth.mgmt.request.QueryOrdersRequest;

import com.todaysoft.ghealth.portal.mgmt.facade.SampleSendMgmtFacade;
import com.todaysoft.ghealth.service.orderEvent.OrderEventLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/mgmt/sampleSend")
public class SampleSendMgmtController {
    @Autowired
    private SampleSendMgmtFacade facade;
    @RequestMapping("pager")
    public PagerResponse<Order> pager(@RequestBody QueryOrdersRequest request)
    {
        return facade.pager(request);
    }

    @RequestMapping("/modifySendStatus")
    @OrderEventLog(eventType = "4",handler = "mgmt")
    public Map<String,String> modifySendStatus(@RequestBody MaintainSimpleRequest request)
    {
       return facade.modifySendStatus(request);
    }
}
