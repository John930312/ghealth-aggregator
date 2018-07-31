package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.SampleSignDetailsMapper;
import com.todaysoft.ghealth.mybatis.mapper.SampleSignMapper;
import com.todaysoft.ghealth.mybatis.model.SampleSign;
import com.todaysoft.ghealth.mybatis.model.SampleSignDetails;
import com.todaysoft.ghealth.service.ISampleSignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleSignService implements ISampleSignService
{
    @Autowired
    private SampleSignDetailsMapper mapper;
    
    @Autowired
    private SampleSignMapper sampleSignMapper;
    
    @Override
    public SampleSignDetails getByOrderId(String orderId)
    {
        return mapper.getByOrderId(orderId);
    }
    
    @Override
    public SampleSign getBySignRecordId(String id)
    {
        return sampleSignMapper.getBySignRecordId(id);
    }
    
    @Override
    public int count(Object searcher)
    {
        return 0;
    }
    
    @Override
    public List<SampleSign> query(Object searcher, int offset, int limit)
    {
        return null;
    }
}
