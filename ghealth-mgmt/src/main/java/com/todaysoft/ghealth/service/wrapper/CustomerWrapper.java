package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.mgmt.request.QueryCustomersRequest;
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.model.customer.Customer;

@Component
public class CustomerWrapper
{
    public List<Customer> wrap(List<com.todaysoft.ghealth.base.response.model.Customer> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        
        Customer customer;
        List<Customer> customers = new ArrayList<Customer>();
        
        for (com.todaysoft.ghealth.base.response.model.Customer record : records)
        {
            customer = new Customer();
            wrapRecord(record, customer);
            customers.add(customer);
        }
        
        return customers;
    }
    
    public Customer wrap(com.todaysoft.ghealth.base.response.model.Customer record)
    {
        if (null == record)
        {
            return null;
        }
        
        Customer customer = new Customer();
        wrapRecord(record, customer);
        return customer;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.Customer source, Customer target)
    {
        BeanUtils.copyProperties(source, target, "createTime", "updateTime", "deleteTime");
        target.setCreateTime(null == source.getCreateTime() ? null : new Date(source.getCreateTime()));
        target.setUpdateTime(null == source.getUpdateTime() ? null : new Date(source.getUpdateTime()));
    }

    public QueryCustomersRequest searcherCustomerWarp(CustomerSearcher searcher, QueryCustomersRequest request)
    {
        if (null != searcher.getStartCreateTime())
        {
            request.setStartCreateTime(String.valueOf(searcher.getStartCreateTime().getTime()));
        }
        if (null != searcher.getEndStartTime())
        {
            request.setEndStartTime(String.valueOf(searcher.getEndStartTime().getTime()));
        }
        return request;
    }
}
