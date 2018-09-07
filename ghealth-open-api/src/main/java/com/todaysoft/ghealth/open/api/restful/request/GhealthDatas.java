package com.todaysoft.ghealth.open.api.restful.request;

import com.todaysoft.ghealth.open.api.mybatis.model.Customer;
import com.todaysoft.ghealth.open.api.mybatis.model.Order;
import com.todaysoft.ghealth.open.api.mybatis.model.OrderRequest;

/**
 * @Author: xjw
 * @Date: 2018/9/4 10:17
 */
public class GhealthDatas
{
    private OrderRequest order;
    
    private Customer customer;
    
    public OrderRequest getOrder()
    {
        return order;
    }
    
    public void setOrder(OrderRequest order)
    {
        this.order = order;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
}
