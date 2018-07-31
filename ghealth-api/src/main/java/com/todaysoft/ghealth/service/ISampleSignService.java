package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.SampleSign;
import com.todaysoft.ghealth.mybatis.model.SampleSignDetails;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ISampleSignService extends PagerQueryHandler<SampleSign>
{
    SampleSignDetails getByOrderId(String orderId);

    SampleSign getBySignRecordId(String id);
}
