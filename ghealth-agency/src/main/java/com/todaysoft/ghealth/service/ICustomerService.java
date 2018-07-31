package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.model.customer.Customer;
import com.todaysoft.ghealth.model.customer.CustomerForm;
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ICustomerService
{
    Pagination<Customer> searcher(CustomerSearcher searcher, int pageNo, int pageSize);
    
    List<Customer> list(CustomerSearcher searcher);
    
    Customer get(String id);
    
    void create(CustomerForm data);
    
    void modify(CustomerForm data);

    boolean delete(String id);

    boolean isPhoneUnique(String id, String phone);
}
