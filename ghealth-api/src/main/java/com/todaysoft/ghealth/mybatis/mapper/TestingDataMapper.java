package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.OrderTestingData;
import com.todaysoft.ghealth.mybatis.model.TestingDataUploadRecord;
import com.todaysoft.ghealth.mybatis.searcher.TestingDataUploadRecordSearcher;

public interface TestingDataMapper
{
    int count(TestingDataUploadRecordSearcher searcher);
    
    List<TestingDataUploadRecord> list(TestingDataUploadRecordSearcher searcher);
    
    TestingDataUploadRecord get(String id);
    
    void insertUploadRecord(TestingDataUploadRecord data);
    
    OrderTestingData getOrderTestingData(String orderId);
    
    void deleteOrderTestingData(String orderId);
    
    void insertOrderTestingData(OrderTestingData data);
}
