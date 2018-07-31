package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SampleDelivery;

public interface SampleDeliveryMapper
{
    int create(SampleDelivery record);
    
    SampleDelivery getByBeliveryRecordId(String id);
    
    int modify(SampleDelivery record);
    
}