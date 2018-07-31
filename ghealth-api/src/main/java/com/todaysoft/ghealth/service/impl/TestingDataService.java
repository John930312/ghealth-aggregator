package com.todaysoft.ghealth.service.impl;

import java.util.*;

import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.utils.DataStatus;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.fasterxml.jackson.core.type.TypeReference;
import com.todaysoft.ghealth.mybatis.mapper.TestingDataMapper;
import com.todaysoft.ghealth.mybatis.model.LocusGenetype;
import com.todaysoft.ghealth.mybatis.model.OrderTestingData;
import com.todaysoft.ghealth.mybatis.model.TestingDataUploadRecord;
import com.todaysoft.ghealth.mybatis.searcher.TestingDataUploadRecordSearcher;
import com.todaysoft.ghealth.service.ITestingDataService;
import com.todaysoft.ghealth.utils.JsonUtils;

@Service
public class TestingDataService implements ITestingDataService
{
    @Autowired
    private TestingDataMapper mapper;

    @Autowired
    private IOrderService orderService;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof TestingDataUploadRecordSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((TestingDataUploadRecordSearcher)searcher);
    }
    
    @Override
    public List<TestingDataUploadRecord> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof TestingDataUploadRecordSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            TestingDataUploadRecordSearcher tis = (TestingDataUploadRecordSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.list((TestingDataUploadRecordSearcher)searcher);
    }
    
    @Override
    public TestingDataUploadRecord get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public Map<String,String> upload(TestingDataUploadRecord uploadRecord, List<OrderTestingData> testingDatas)
    {
        Map<String,String> map = new HashMap<>();
        mapper.insertUploadRecord(uploadRecord);

        if (!CollectionUtils.isEmpty(testingDatas))
        {
            for (OrderTestingData testingData : testingDatas)
            {
                mapper.deleteOrderTestingData(testingData.getOrderId());
                mapper.insertOrderTestingData(testingData);
//                if (testingData.isFlag())
//                {
                    Order order = orderService.getOrderById(testingData.getOrderId());
                    order.setStatus(DataStatus.ORDER_UPLOADED);
                    orderService.modify(order);
//                }
                map.put(testingData.getOrderId(),testingData.getId());
            }
        }
        return map;
    }
    
    @Override
    public List<LocusGenetype> getOrderTestingData(String orderId)
    {
        OrderTestingData testingData = mapper.getOrderTestingData(orderId);
        
        if (null == testingData)
        {
            return Collections.emptyList();
        }
        
        String details = testingData.getDetails();
        
        if (StringUtils.isEmpty(details))
        {
            return Collections.emptyList();
        }
        
        return JsonUtils.fromJson(details, new TypeReference<List<LocusGenetype>>()
        {
        });
    }
}
