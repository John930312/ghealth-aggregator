package com.todaysoft.ghealth.open.api.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.open.api.mybatis.model.*;

public interface OrderMapper
{
    void create(OrderRequest data);

    long count(OrderQuery query);
    
    List<Order> query(OrderQuery query);
    
    Order get(String id);

    List<TestingItemEvaluation> getOrderTestingItemEvaluations(String id);

    Order getEntityByCode(String code);

    ObjectStorage getPdfReportUrl(String orderId);
}