package com.todaysoft.ghealth.open.api.service.wrapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.open.api.mybatis.model.Order;
import com.todaysoft.ghealth.open.api.restful.model.OrderDTO;

@Component
public class OrderWrapper extends Wrapper<Order, OrderDTO>
{
    @Autowired
    private AgencyWrapper agencyWrapper;
    
    @Autowired
    private ProductWrapper productWrapper;
    
    @Autowired
    private CustomerWrapper customerWrapper;
    
    @Autowired
    private OrderHistoryWrapper orderHistoryWrapper;
    
    @Override
    protected String[] getIgnoreProperties()
    {
        return new String[] {"agency", "product", "customer", "orderHistoryList", "submitTime", "createTime"};
    }
    
    @Override
    protected void setIgnoreProperties(Order source, OrderDTO target)
    {
        target.setCreateTime(format(source.getCreateTime()));
        target.setSubmitTime(format(source.getSubmitTime()));
        target.setReportGenerateTime(format(source.getReportGenerateTime()));
        target.setAgency(agencyWrapper.wrap(source.getAgency()));
        target.setProduct(productWrapper.wrap(source.getProduct()));
        target.setCustomer(customerWrapper.wrap(source.getCustomer()));
        target.setOrderHistoryList(orderHistoryWrapper.wrap(source.getOrderHistoryList()));
        
    }
}
