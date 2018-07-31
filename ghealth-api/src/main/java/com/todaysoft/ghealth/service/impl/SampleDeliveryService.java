package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.SampleDeliveryDetailsMapper;
import com.todaysoft.ghealth.mybatis.mapper.SampleDeliveryMapper;
import com.todaysoft.ghealth.mybatis.model.SampleDelivery;
import com.todaysoft.ghealth.mybatis.model.SampleDeliveryDetails;
import com.todaysoft.ghealth.service.ISampleDeliveryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SampleDeliveryService implements ISampleDeliveryService
{
    @Autowired
    private SampleDeliveryMapper sampleDeliveryMapper;
    
    @Autowired
    private SampleDeliveryDetailsMapper sampleDeliveryDetailsMapper;
    
    @Override
    public SampleDelivery getByBeliveryRecordId(String id)
    {
        return sampleDeliveryMapper.getByBeliveryRecordId(id);
    }
    
    @Override
    public SampleDeliveryDetails getByOrderId(String orderId)
    {
        return sampleDeliveryDetailsMapper.getByOrderId(orderId).get(0);
    }
    
    @Override
    public int count(Object searcher)
    {
        return 0;
    }
    
    @Override
    public List<SampleDelivery> query(Object searcher, int offset, int limit)
    {
        return null;
    }
}
