package com.todaysoft.ghealth.wechat.service.impl;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.wechat.model.Order;
import com.todaysoft.ghealth.wechat.model.ProductDetails;
import com.todaysoft.ghealth.wechat.service.IOrderService;
import com.todaysoft.ghealth.wechat.service.core.AccountContextHolder;
import com.todaysoft.ghealth.wechat.service.core.Gateway;
import com.todaysoft.ghealth.wechat.service.wrapper.OrderWrapper;
import org.apache.poi.util.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

@Service
public class OrderServiceImpl implements IOrderService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private AccountContextHolder accountContextHolder;
    
    @Override
    public boolean existDatas(String phone)
    {
        DataResponse<Boolean> response = gateway.get("/wechat/order/existDatas/{phone}", new ParameterizedTypeReference<DataResponse<Boolean>>()
        {
        }, phone);
        return response.getData();
    }
    
    @Override
    public List<Order> getOrders()
    {
        String token = accountContextHolder.getAccount().getToken();
        DataResponse<List<com.todaysoft.ghealth.base.response.model.Order>> orderDataResponse =
            gateway.get("/wechat/order/getOrders/{token}", new ParameterizedTypeReference<DataResponse<List<com.todaysoft.ghealth.base.response.model.Order>>>()
            {
            }, token);
        
        if (CollectionUtils.isEmpty(orderDataResponse.getData()))
        {
            return new ArrayList<>();
        }
        
        return orderWrapper.wrap(orderDataResponse.getData());
    }
    
    @Override
    public List<ProductDetails> getHostProducts()
    {
        DataResponse<List<ProductDetails>> orderDataResponse =
            gateway.get("/wechat/testing-products/getHostProducts", new ParameterizedTypeReference<DataResponse<List<ProductDetails>>>()
            {
            }, "123");
        return orderDataResponse.getData();
    }
    
    @Override
    public Order getOrderById(String id)
    {
        DataResponse<com.todaysoft.ghealth.base.response.model.Order> orderDataResponse =
            gateway.get("/wechat/order/getOrderById/{id}", new ParameterizedTypeReference<DataResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            }, id);
        com.todaysoft.ghealth.base.response.model.Order data = orderDataResponse.getData();
        
        return orderWrapper.wrap(data);
    }
    
    @Override
    public void preview(byte[] bytes, HttpServletResponse response) throws IOException
    {
        InputStream in = new ByteArrayInputStream(bytes);
        
        OutputStream out = null;
        try
        {
            response.setContentType("application/pdf;charset=UTF-8");
            out = response.getOutputStream();
            IOUtils.copy(in, out);
            out.flush();
        }
        finally
        {
            if (null != in)
            {
                in.close();
            }
            
            if (null != out)
            {
                out.close();
            }
        }
    }
}
