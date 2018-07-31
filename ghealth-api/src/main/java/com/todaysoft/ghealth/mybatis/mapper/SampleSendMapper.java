package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.model.OrderDetails;
import com.todaysoft.ghealth.mybatis.model.OrderSignIn;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;

import java.util.List;

public interface SampleSendMapper {
    List<Order> search(OrderSearcher searcher);

    int count(OrderSearcher searcher);

    void modifyStatus(Order data);

    void createOperate(OrderSignIn orderSignIn);

    Order getInfo(String orderCode);

    void createOrderDetails(OrderDetails orderDetails);
}
