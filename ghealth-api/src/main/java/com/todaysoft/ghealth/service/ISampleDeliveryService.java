package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.SampleDelivery;
import com.todaysoft.ghealth.mybatis.model.SampleDeliveryDetails;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ISampleDeliveryService extends PagerQueryHandler<SampleDelivery>
{
    SampleDelivery getByBeliveryRecordId(String id);
    
    SampleDeliveryDetails getByOrderId(String orderId);
}
