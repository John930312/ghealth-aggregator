package com.todaysoft.ghealth.portal.agcy.mvc;

import com.todaysoft.ghealth.agcy.request.MaintainAgcyMessageSendRequest;
import com.todaysoft.ghealth.agcy.request.QueryAgcyMessageSendRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.MessageSend;
import com.todaysoft.ghealth.portal.agcy.facade.AgcyMessageSendFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/agcy/messageSend")
public class AgcyMessageSendController {
    @Autowired
    private AgcyMessageSendFacade facade;


    @RequestMapping("/pager")
    public PagerResponse<MessageSend> pager(@RequestBody QueryAgcyMessageSendRequest request)
    {
        return facade.pager(request);
    }


    @RequestMapping("/get")
    public ObjectResponse<MessageSend> get(@RequestBody MaintainAgcyMessageSendRequest request)
    {
        return facade.get(request);
    }
}
