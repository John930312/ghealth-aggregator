package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.MessageSendMapper;
import com.todaysoft.ghealth.mybatis.model.MessageSend;
import com.todaysoft.ghealth.mybatis.model.MessageSendAgency;
import com.todaysoft.ghealth.mybatis.searcher.MessageSendSearcher;
import com.todaysoft.ghealth.service.IMessageSendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
public class MessageSendService implements IMessageSendService {

    @Autowired
    private MessageSendMapper mapper;


    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof MessageSendSearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((MessageSendSearcher)searcher);
    }

    @Override
    public List<MessageSend> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof MessageSendSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            MessageSendSearcher tis = (MessageSendSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((MessageSendSearcher)searcher);
    }

    @Override
    @Transactional
    public void create(MessageSend messageSend)
    {
        mapper.create(messageSend);
    }


    @Override
    @Transactional
    public void modify(MessageSend messageSend)
    {
        mapper.modify(messageSend);
    }


    @Override
    public MessageSend get(String id)
    {
        return mapper.get(id);
    }


    @Override
    @Transactional
    public void createMessageAgency(MessageSendAgency messageSendAgency)
    {
        mapper.createMessageAgency(messageSendAgency);
    }

    @Override
    public boolean isTitleUnique(String title)
    {
        MessageSendSearcher searcher = new MessageSendSearcher();
        searcher.setTitle(title);
        searcher.setNameExactMatches(true);
        int count = mapper.count(searcher);
        return count == 0;
    }

    @Override
    public List<String> getAgencyName(String id){
        return mapper.getAgencyName(id);
    }


    @Override
    @Transactional
    public void delete(String id)
    {
        mapper.delete(id);
    }


    @Override
    @Transactional
    public void deleteById(String id)
    {
        mapper.deleteById(id);
    }

}
