package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.mgmt.request.QueryMessageSendRequest;
import com.todaysoft.ghealth.model.messageSend.MessageSend;
import com.todaysoft.ghealth.model.messageSend.MessageSendSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class MessageSendWrapper {
    public List<MessageSend> wrap(List<com.todaysoft.ghealth.base.response.model.MessageSend> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        MessageSend messageSend;
        List<MessageSend> messageSends = new ArrayList<MessageSend>();
        for (com.todaysoft.ghealth.base.response.model.MessageSend record : records)
        {
            messageSend = new MessageSend();
            wrapRecord(record, messageSend);
            messageSends.add(messageSend);
        }
        return messageSends;
    }

    public MessageSend wrap(com.todaysoft.ghealth.base.response.model.MessageSend record)
    {
        if (null == record)
        {
            return null;
        }

        MessageSend messageSend = new MessageSend();
        wrapRecord(record, messageSend);
        return messageSend;
    }

    private void wrapRecord(com.todaysoft.ghealth.base.response.model.MessageSend source, MessageSend target)
    {
        BeanUtils.copyProperties(source, target, "pushTime");
        target.setPushTime(null == source.getPushTime() ? null : new Date(source.getPushTime()));
    }


    public QueryMessageSendRequest searcherWarp(MessageSendSearcher searcher)
    {
        QueryMessageSendRequest request = new QueryMessageSendRequest();
        BeanUtils.copyProperties(searcher, request, "startTime", "endTime");
        if (null != searcher.getStartTime())
        {
            request.setStartTime(String.valueOf(searcher.getStartTime().getTime()));
        }
        if (null != searcher.getEndTime())
        {
            request.setEndTime(String.valueOf(searcher.getEndTime().getTime()));
        }
        return request;
    }

}
