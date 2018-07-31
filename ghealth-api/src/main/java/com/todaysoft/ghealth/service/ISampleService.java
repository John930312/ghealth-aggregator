package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderSignIn;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

import java.util.List;
import java.util.Map;

public interface ISampleService extends PagerQueryHandler<Order> {
    List<Order> list(OrderSearcher searcher);

    void modifyStatus(Order data);

    boolean isStatus(String id);

    Order getInformation(String orderCode);

    void createOperate(OrderSignIn orderSignIn);

    Map<String,String> sampleOperation(String orderCodes, String status, String operateName);

}
