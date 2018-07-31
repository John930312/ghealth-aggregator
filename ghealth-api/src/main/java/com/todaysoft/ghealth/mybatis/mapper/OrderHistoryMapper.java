package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.searcher.StatisticsSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface OrderHistoryMapper
{
    int delete(String id);
    
    int create(OrderHistory record);
    
    OrderHistory get(String id);
    
    int modify(OrderHistory record);

    List<OrderHistory> getOrderHistoriesByOrderId(String orderId);

    List<OrderHistory> getOrderHistories(@Param("name")String name,@Param("year")String year, @Param("month")String month);

    List<OrderHistory> getOrderHistoryLists(@Param("name")String name,@Param("year")String year, @Param("month")String month, @Param("day")String day);

    List<OrderHistory> getOrderHistory(StatisticsSearcher searcher);

    List<String> getOrderIds(StatisticsSearcher searcher);

    int deleteByOrderId(String id);
}