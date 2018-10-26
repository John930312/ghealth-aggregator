package com.todaysoft.ghealth.open.api.mybatis.mapper;

import com.todaysoft.ghealth.open.api.mybatis.model.*;

import java.util.List;

public interface OrderMapper
{
    void create(OrderRequest data);

    long count(OrderQuery query);
    
    List<Order> query(OrderQuery query);
    
    Order get(String id);

    List<TestingItemEvaluation> getOrderTestingItemEvaluations(String id);

    Order getEntityByCode(String code);

    ObjectStorage getPdfReportUrl(String orderId);

    long countByCode(String code);

    AgencyProduct getAgencyProduct(AgencyProductSearcher searcher);
}