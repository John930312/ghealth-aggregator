package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.messageSend.MessageSend;
import com.todaysoft.ghealth.model.messageSend.MessageSendSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface IMessageSendService {
    Pagination<MessageSend> pagination(MessageSendSearcher searcher, int pageNo, int pageSize);

    List<Agency> getAgencyList();

    void create(MessageSend messageSend);

    MessageSend get(String id);

    boolean isTitleUnique(String title);

    void modify(MessageSend messageSend);

    void delete(String id);
}
