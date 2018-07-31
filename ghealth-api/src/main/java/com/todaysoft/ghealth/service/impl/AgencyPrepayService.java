package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.AgencyPrepayMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyPrepay;
import com.todaysoft.ghealth.service.IAgencyPrepayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgencyPrepayService implements IAgencyPrepayService
{
    @Autowired
    private AgencyPrepayMapper mapper;
    
    @Override
    public void create(AgencyPrepay data)
    {
        mapper.insert(data);
    }
    
    @Override
    public void modify(AgencyPrepay data)
    {
        mapper.update(data);
    }

    @Override
    public AgencyPrepay get(String id)
    {
        return mapper.get(id);
    }
}
