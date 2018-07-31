package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.agcy.request.MaintainAgcyMessageSendRequest;
import com.todaysoft.ghealth.agcy.request.QueryAgcyMessageSendRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.QueryMessageSendRequest;
import com.todaysoft.ghealth.model.messageSend.MessageSend;
import com.todaysoft.ghealth.model.messageSend.MessageSendSearcher;
import com.todaysoft.ghealth.service.IMessageSendService;
import com.todaysoft.ghealth.service.wrapper.MessageSendWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

@Service
public class MessageSendService implements IMessageSendService{


    @Autowired
    private Gateway gateway;
    @Autowired
    private MessageSendWrapper wrapper;

    @Override
    public Pagination<MessageSend> pagination(MessageSendSearcher searcher, int pageNo, int pageSize)
    {
        QueryAgcyMessageSendRequest request = wrapper.searcherWarp(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.MessageSend> response = gateway.request("/agcy/messageSend/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.MessageSend>>()
        {
        });

        Pager<com.todaysoft.ghealth.base.response.model.MessageSend> pager = response.getData();
        Pagination<MessageSend> pagination = new Pagination<MessageSend>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }

    @Override
    public MessageSend get(String id)
    {
        MaintainAgcyMessageSendRequest request = new MaintainAgcyMessageSendRequest();
        request.setId(id);

        ObjectResponse<com.todaysoft.ghealth.base.response.model.MessageSend> response = gateway.request("/agcy/messageSend/get", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.MessageSend>>()
        {
        });

        return wrapper.wrap(response.getData());
    }
}
