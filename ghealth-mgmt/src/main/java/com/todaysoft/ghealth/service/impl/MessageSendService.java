package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainMessageSendRequest;
import com.todaysoft.ghealth.mgmt.request.QueryMessageSendRequest;
import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencySearcher;
import com.todaysoft.ghealth.model.messageSend.MessageSend;
import com.todaysoft.ghealth.model.messageSend.MessageSendSearcher;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.IMessageSendService;
import com.todaysoft.ghealth.service.wrapper.MessageSendWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MessageSendService implements IMessageSendService {
    @Autowired
    private IAgencyService agencyService;

    @Autowired
    private Gateway gateway;

    @Autowired
    private MessageSendWrapper wrapper;


    @Override
    public Pagination<MessageSend> pagination(MessageSendSearcher searcher, int pageNo, int pageSize)
    {
        QueryMessageSendRequest request = wrapper.searcherWarp(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.MessageSend> response = gateway.request("/mgmt/messageSend/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.MessageSend>>()
        {
        });

        Pager<com.todaysoft.ghealth.base.response.model.MessageSend> pager = response.getData();
        Pagination<MessageSend> pagination = new Pagination<MessageSend>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }


    @Override
    public List<Agency> getAgencyList(){
        List<Agency> list = agencyService.list(new AgencySearcher());
        return list;
    }


    @Override
    public void create(MessageSend messageSend)
    {
        MaintainMessageSendRequest request = new MaintainMessageSendRequest();
        BeanUtils.copyProperties(messageSend, request);
        gateway.request("/mgmt/messageSend/create", request);
    }

    @Override
    public MessageSend get(String id)
    {
        MaintainMessageSendRequest request = new MaintainMessageSendRequest();
        request.setId(id);

        ObjectResponse<com.todaysoft.ghealth.base.response.model.MessageSend> response = gateway.request("/mgmt/messageSend/get", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.MessageSend>>()
        {
        });

        return wrapper.wrap(response.getData());
    }

    @Override
    public boolean isTitleUnique(String title)
    {
        MaintainMessageSendRequest request = new MaintainMessageSendRequest();
        request.setTitle(title);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/messageSend/isTitleUnique", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        if (null == response.getData())
        {
            return false;
        }
        return response.getData();
    }


    @Override
    public void modify(MessageSend messageSend)
    {
        MaintainMessageSendRequest request = new MaintainMessageSendRequest();
        request.setId(messageSend.getId());
        request.setTitle(messageSend.getTitle());
        request.setContent(messageSend.getContent());
        gateway.request("/mgmt/messageSend/modify", request);
    }


    @Override
    public void delete(String id)
    {
        MaintainMessageSendRequest request = new MaintainMessageSendRequest();
        request.setId(id);
        gateway.request("/mgmt/messageSend/delete", request);

    }

}
