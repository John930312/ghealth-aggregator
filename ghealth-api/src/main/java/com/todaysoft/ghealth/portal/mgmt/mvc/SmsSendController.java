package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest;
import com.todaysoft.ghealth.mgmt.request.MaintainSmsSendRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.SmsSendFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: xjw
 * @Date: 2018/7/17 14:40
 */
@RestController
@RequestMapping("/mgmt/smsSend")
public class SmsSendController
{
    @Autowired
    private SmsSendFacade facade;
    
    @RequestMapping("/reportGeneratedSendToAgcy")
    public void reportGeneratedSendToAgcy(@RequestBody MaintainOrderRequest request)
    {
        facade.reportGeneratedSendToAgcy(request);
    }
    
    @RequestMapping("/sampleSignedSend")
    public void sampleSignedSend(@RequestBody MaintainOrderRequest request)
    {
        facade.sampleSignedSend(request);
    }
    
    @RequestMapping("/sampleDeliveredSend")
    public void sampleDeliveredSend(@RequestBody MaintainOrderRequest request)
    {
        facade.sampleDeliveredSend(request);
    }
    
    @RequestMapping("/create")
    public void create(@RequestBody MaintainSmsSendRequest request)
    {
        facade.create(request);
    }
}
