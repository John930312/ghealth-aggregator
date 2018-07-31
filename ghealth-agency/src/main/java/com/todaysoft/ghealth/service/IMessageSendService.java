package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.messageSend.MessageSend;
import com.todaysoft.ghealth.model.messageSend.MessageSendSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface IMessageSendService {
    Pagination<MessageSend> pagination(MessageSendSearcher searcher, int pageNo, int pageSize);

    MessageSend get(String id);
}
