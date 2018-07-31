package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.customer.Customer;
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

public interface ICustomerService
{
    Pagination<Customer> searcher(CustomerSearcher searcher, int pageNo, int pageSize);
    
    Customer get(String id);
    
    void modify(Customer data);
    
    boolean delete(String id);

    List<Customer> list(CustomerSearcher searcher);
}
