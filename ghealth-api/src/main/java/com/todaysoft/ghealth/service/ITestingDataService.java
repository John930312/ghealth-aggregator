package com.todaysoft.ghealth.service;

import java.util.List;
import java.util.Map;

import com.todaysoft.ghealth.mybatis.model.LocusGenetype;
import com.todaysoft.ghealth.mybatis.model.OrderTestingData;
import com.todaysoft.ghealth.mybatis.model.TestingDataUploadRecord;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ITestingDataService extends PagerQueryHandler<TestingDataUploadRecord>
{
    TestingDataUploadRecord get(String id);

    Map<String,String> upload(TestingDataUploadRecord uploadRecord, List<OrderTestingData> testingDatas);
    
    List<LocusGenetype> getOrderTestingData(String orderId);
}
