package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SampleSignDetails;

public interface SampleSignDetailsMapper
{
    int create(SampleSignDetails record);
    
    SampleSignDetails get(String id);
    
    SampleSignDetails getByOrderId(String orderId);
    
    int modify(SampleSignDetails record);
    
}