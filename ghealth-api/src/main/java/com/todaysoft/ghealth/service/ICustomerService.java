package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.searcher.CustomerSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface ICustomerService extends PagerQueryHandler<Customer>
{
    Customer get(String id);
    
    void create(Customer data, AgencyAccountDetails account);
    
    void modify(Customer data);
    
    List<Customer> list(CustomerSearcher searcher);
    
    boolean isPhoneUnique(String id, String phone);
    
    int customerCount(CustomerSearcher searcher);
    
    List<Customer> getCustomerByPhone(String phone);
}
