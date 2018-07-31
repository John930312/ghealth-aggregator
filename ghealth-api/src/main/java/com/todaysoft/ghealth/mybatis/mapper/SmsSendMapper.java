package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SmsSend;
import com.todaysoft.ghealth.mybatis.searcher.SmsSendSearcher;

import java.util.List;

public interface SmsSendMapper
{
    void create(SmsSend record);
    
    void modify(SmsSend record);
    
    List<SmsSend> getDatasInTime(SmsSendSearcher searcher);
}