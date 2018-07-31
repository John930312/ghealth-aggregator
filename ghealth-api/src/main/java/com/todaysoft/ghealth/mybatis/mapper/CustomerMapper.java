package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.searcher.CustomerSearcher;

public interface CustomerMapper
{
    int count(CustomerSearcher searcher);
    
    List<Customer> search(CustomerSearcher searcher);
    
    Customer get(String id);
    
    int insert(Customer record);
    
    int update(Customer record);

    int countByPhone(String phone);

    int customerCount(CustomerSearcher searcher);

    List<Customer> getCustomerByPhone(String phone);
}