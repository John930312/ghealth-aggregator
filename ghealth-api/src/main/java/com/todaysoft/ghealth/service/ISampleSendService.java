package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;
import java.util.Map;

public interface ISampleSendService  extends PagerQueryHandler<Order> {
    List<Order> list(OrderSearcher searcher);

    Map<String,String> sampleSendOperation(String orderCodes, String status, String operateName);
}
