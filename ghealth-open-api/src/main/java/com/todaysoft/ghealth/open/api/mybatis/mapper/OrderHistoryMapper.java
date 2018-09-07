package com.todaysoft.ghealth.open.api.mybatis.mapper;

import com.todaysoft.ghealth.open.api.mybatis.model.OrderHistory;

import java.util.List;

public interface OrderHistoryMapper
{
    List<OrderHistory> getOrderHistoriesByOrderId(String orderId);
}