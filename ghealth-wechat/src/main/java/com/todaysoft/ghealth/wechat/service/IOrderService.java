package com.todaysoft.ghealth.wechat.service;

import com.todaysoft.ghealth.wechat.model.Order;
import com.todaysoft.ghealth.wechat.model.ProductDetails;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

public interface IOrderService
{
    boolean existDatas(String phone);
    
    List<Order> getOrders();
    
    List<ProductDetails> getHostProducts();
    
    Order getOrderById(String id);
    
    void preview(byte[] bytes, HttpServletResponse response) throws IOException;
}
