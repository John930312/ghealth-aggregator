package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.MessageSend;
import com.todaysoft.ghealth.service.IMessageSendService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class MessageSendWrapper {

    @Autowired
    private IMessageSendService service;

    public List<MessageSend> wrap(List<com.todaysoft.ghealth.mybatis.model.MessageSend> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        MessageSend messageSend;
        List<MessageSend> messageSends = new ArrayList<MessageSend>();
        for (com.todaysoft.ghealth.mybatis.model.MessageSend record : records)
        {
            messageSend = new MessageSend();
            wrap(record, messageSend);
            messageSends.add(messageSend);
        }

        return messageSends;
    }

    public MessageSend wrap(com.todaysoft.ghealth.mybatis.model.MessageSend record)
    {
        MessageSend messageSend = new MessageSend();
        wrap(record, messageSend);
        return messageSend;
    }

    private void wrap(com.todaysoft.ghealth.mybatis.model.MessageSend source, MessageSend target)
    {
        BeanUtils.copyProperties(source, target, "pushTime");

        target.setPushTime(null == source.getPushTime() ? null : source.getPushTime().getTime());

        List<String> agencyName = service.getAgencyName(source.getId());

        target.setAgencyName(agencyName);


    }
}
