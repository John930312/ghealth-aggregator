package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.OrderHistoryMapper;
import com.todaysoft.ghealth.mybatis.model.OrderHistory;
import com.todaysoft.ghealth.mybatis.searcher.StatisticsSearcher;
import com.todaysoft.ghealth.service.IOrderHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderHistoryService implements IOrderHistoryService
{
    @Autowired
    private OrderHistoryMapper mapper;
    
    @Override
    public void create(OrderHistory data)
    {
        mapper.create(data);
    }
    
    @Override
    public List<OrderHistory> getOrderHistoriesByOrderId(String id)
    {
        return mapper.getOrderHistoriesByOrderId(id);
    }

    @Override
    public List<OrderHistory> getOrderHistories(String name,String year,String month)
    {
        return mapper.getOrderHistories(name,year,month);
    }

    @Override
    public List<OrderHistory> getOrderHistoryLists(String name,String year,String month,String day)
    {
        return mapper.getOrderHistoryLists(name,year,month,day);
    }

    @Override
    public List<OrderHistory> getOrderHistory(StatisticsSearcher searcher)
    {
        return mapper.getOrderHistory(searcher);
    }

    @Override
    public List<String> getOrderIds(StatisticsSearcher searcher)
    {
        return mapper.getOrderIds(searcher);
    }

    @Override
    public void deleteByOrderId(String id)
    {
        mapper.deleteByOrderId(id);
    }

}
