package com.todaysoft.ghealth.portal.wechat.mvc;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.base.response.model.Order;
import com.todaysoft.ghealth.portal.wechat.facade.WechatOrderFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/wechat/order")

public class WechatOrderController
{
    @Autowired
    private WechatOrderFacade facade;
    
    @RequestMapping("/getOrders/{token}")
    public DataResponse<List<Order>> getOrders(@PathVariable String token)
    {
        return facade.getOrders(token);
    }
    
    @RequestMapping("/getOrderById/{id}")
    public DataResponse<Order> getOrderById(@PathVariable String id) throws IOException
    {
        return facade.getOrderById(id);
    }
    
    @RequestMapping("/existDatas/{phone}")
    public DataResponse<Boolean> existDatas(@PathVariable String phone)
    {
        return facade.existDatas(phone);
    }
}
