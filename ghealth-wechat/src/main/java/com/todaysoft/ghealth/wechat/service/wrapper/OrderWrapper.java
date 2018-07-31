package com.todaysoft.ghealth.wechat.service.wrapper;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.wechat.model.Order;

@Component
public class OrderWrapper
{
    @Autowired
    private CustomerWrapper customerWrapper;
    
    @Autowired
    private AgencyWrapper agencyWrapper;
    
    @Autowired
    private ProductDetailsWrapper productDetailsWrapper;
    
    @Autowired
    private OrderHistoryWrapper orderHistoryWrapper;
    
    public List<Order> wrap(List<com.todaysoft.ghealth.base.response.model.Order> datas)
    {
        if (CollectionUtils.isEmpty(datas))
        {
            return new ArrayList<>();
        }
        return datas.stream().map(x -> wrap(x)).collect(Collectors.toList());
    }
    
    public Order wrap(com.todaysoft.ghealth.base.response.model.Order source)
    {
        Order target = new Order();
        wrap(source, target);
        return target;
    }
    
    private void wrap(com.todaysoft.ghealth.base.response.model.Order source, Order target)
    {
        BeanUtils.copyProperties(source, target);
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setSubmitTime(null == source.getSubmitTime() ? null : new Date(source.getSubmitTime()));
        
        Optional.ofNullable(source.getProduct()).ifPresent(x -> target.setProduct(productDetailsWrapper.wrap(x)));
        Optional.ofNullable(source.getAgency()).ifPresent(x -> target.setAgency(agencyWrapper.wrap(x)));
        Optional.ofNullable(source.getCustomer()).ifPresent(x -> target.setCustomer(customerWrapper.wrap(x)));
        
        if (!CollectionUtils.isEmpty(source.getOrderHistoryList()))
        {
            target.setOrderHistoryList(orderHistoryWrapper.wrap(source.getOrderHistoryList()));
        }
    }
}
