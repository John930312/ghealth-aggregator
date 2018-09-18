package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.searcher.SmsSendSearcher;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.mybatis.mapper.SmsSendMapper;
import com.todaysoft.ghealth.mybatis.model.SmsSend;
import com.todaysoft.ghealth.service.ISMSSendService;

import java.util.Date;
import java.util.List;

/**
 * @Author: xjw
 * @Date: 2018/7/16 14:38
 */
@Service
public class SMSSendService implements ISMSSendService
{
    @Autowired
    private SmsSendMapper mapper;
    
    @Override
    public void create(SmsSend data)
    {
        mapper.create(data);
    }
    
    @Override
    public void modify(SmsSend data)
    {
        mapper.modify(data);
    }
    
    @Override
    public List<SmsSend> getDatasInTime(SmsSendSearcher searcher)
    {
        return mapper.getDatasInTime(searcher);
    }
    
    @Override
    public List<SmsSend> getFestivalDatasInTime(SmsSendSearcher searcher)
    {
        return mapper.getFestivalDatasInTime(searcher);
    }

    @Override
    public Boolean isUniqueTemplate(String date, String templateId)
    {
        String template = mapper.getTemplateIdByDate(date);
        if (StringUtils.isEmpty(template)){
            return true;
        }
        if (template.equals(templateId)){
            return true;
        }
        return false;
    }
}
