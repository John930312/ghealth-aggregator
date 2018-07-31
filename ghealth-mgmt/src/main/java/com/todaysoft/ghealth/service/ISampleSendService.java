package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ISampleSendService
{
    Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize);
    
    void send(Order data);
    
    void sampleDeliveredSend(Order data);
}
