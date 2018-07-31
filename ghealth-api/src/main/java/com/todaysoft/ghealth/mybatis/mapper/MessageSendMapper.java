package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.MessageSend;
import com.todaysoft.ghealth.mybatis.model.MessageSendAgency;
import com.todaysoft.ghealth.mybatis.searcher.MessageSendSearcher;

import java.util.List;

public interface MessageSendMapper {

    List<MessageSend> search(MessageSendSearcher searcher);

    int count(MessageSendSearcher searcher);

    void create(MessageSend data);

    MessageSend get(String id);

    void createMessageAgency(MessageSendAgency messageSendAgency);

    List<String> getAgencyName(String id);

    void modify(MessageSend data);

    void delete(String id);

    void deleteById(String id);
}
