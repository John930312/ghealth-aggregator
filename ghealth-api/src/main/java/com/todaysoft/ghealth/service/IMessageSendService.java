package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.MessageSend;
import com.todaysoft.ghealth.mybatis.model.MessageSendAgency;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;

public interface IMessageSendService extends PagerQueryHandler<MessageSend> {

    void create(MessageSend messageSend);

    MessageSend get(String id);

    void createMessageAgency(MessageSendAgency messageSendAgency);

    boolean isTitleUnique(String title);

    List<String> getAgencyName(String id);

    void modify(MessageSend messageSend);

    void delete(String id);

    void deleteById(String id);
}
