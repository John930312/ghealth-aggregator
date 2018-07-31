package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.searcher.StatisticsSearcher;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IOrderHistoryService
{
    void create(OrderHistory data);
    
    List<OrderHistory> getOrderHistoriesByOrderId(String id);

    List<OrderHistory> getOrderHistories(String name,String year,String month);

    List<OrderHistory> getOrderHistoryLists(@Param("name")String name,@Param("year")String year, @Param("month")String month, @Param("day")String day);

    List<OrderHistory> getOrderHistory(StatisticsSearcher searcher);

    List<String> getOrderIds(StatisticsSearcher searcher);

    void deleteByOrderId(String id);
}
