package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SampleDeliveryDetails;

import java.util.List;

public interface SampleDeliveryDetailsMapper
{
    int create(SampleDeliveryDetails record);
    
    SampleDeliveryDetails get(String id);
    
    List<SampleDeliveryDetails> getByOrderId(String orderId);
    
    int modify(SampleDeliveryDetails record);
    
}