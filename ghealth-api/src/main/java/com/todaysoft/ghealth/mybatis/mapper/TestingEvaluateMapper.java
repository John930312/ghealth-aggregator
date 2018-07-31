package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.TestingItemEvaluateRecord;
import com.todaysoft.ghealth.mybatis.model.TestingItemLocusEvaluateRecord;
import com.todaysoft.ghealth.service.model.reportData.ItemData;
import com.todaysoft.ghealth.service.model.reportData.LocusData;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TestingEvaluateMapper
{
    void deleteOrderTestingItemEvaluateRecords(String orderId);
    
    void insertTestingItemEvaluateRecord(TestingItemEvaluateRecord entity);
    
    void deleteOrderTestingItemLocusEvaluateRecords(String orderId);
    
    void insertTestingItemLocusEvaluateRecord(TestingItemLocusEvaluateRecord entity);
    
    List<ItemData> getItemDatas(String orderId);
    
    List<LocusData> getLocusDatas(@Param("orderId") String orderId, @Param("itemId") String itemId);
}
