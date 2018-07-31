package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.MessageSend;
import com.todaysoft.ghealth.mgmt.request.MaintainMessageSendRequest;
import com.todaysoft.ghealth.mgmt.request.QueryMessageSendRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.MessageSendMgmtFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mgmt/messageSend")
public class MessageSendMgmtController {
    @Autowired
    private MessageSendMgmtFacade facade;

    @RequestMapping("/pager")
    public PagerResponse<MessageSend> pager(@RequestBody QueryMessageSendRequest request)
    {
        return facade.pager(request);
    }

    @RequestMapping("/create")
    public void create(@RequestBody MaintainMessageSendRequest request)
    {
        facade.create(request);
    }


    @RequestMapping("/get")
    public ObjectResponse<MessageSend> get(@RequestBody MaintainMessageSendRequest request)
    {
        return facade.get(request);
    }
    @RequestMapping("/isTitleUnique")
    public ObjectResponse<Boolean> isTitleUnique(@RequestBody MaintainMessageSendRequest request)
    {
        return facade.isTitleUnique(request);
    }

    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainMessageSendRequest request)
    {
        facade.modify(request);
    }

    @RequestMapping("/delete")
    public void delete(@RequestBody MaintainMessageSendRequest request)
    {
        facade.delete(request);
    }
}
