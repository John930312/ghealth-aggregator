package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.SmsSend;
import com.todaysoft.ghealth.mybatis.searcher.SmsSendSearcher;

import java.util.Date;
import java.util.List;

/**
 * @Author: xjw
 * @Date: 2018/7/16 14:37
 */
public interface ISMSSendService
{
    void create(SmsSend data);
    
    void modify(SmsSend data);

    List<SmsSend> getDatasInTime(SmsSendSearcher searcher);
    
    List<SmsSend> getFestivalDatasInTime(SmsSendSearcher searcher);

    Boolean isUniqueTemplate(String date, String templateId);
}
