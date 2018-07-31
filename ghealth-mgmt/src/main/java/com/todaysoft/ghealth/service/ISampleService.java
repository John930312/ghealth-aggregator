package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ISampleService
{
    Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize);
    
    void modifyStatus(Order data);
    
    boolean status(Order data);
    
    Order getInformation(String orderCode);
    
    void sampleSignedSend(Order data);
}
