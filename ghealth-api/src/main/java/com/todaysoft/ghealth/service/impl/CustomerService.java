package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.CustomerMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.searcher.CustomerSearcher;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.utils.IdGen;

@Service
public class CustomerService implements ICustomerService
{
    
    @Autowired(required = false)
    private CustomerMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof CustomerSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((CustomerSearcher)searcher);
    }
    
    @Override
    public List<Customer> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof CustomerSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            CustomerSearcher tis = (CustomerSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((CustomerSearcher)searcher);
    }
    
    @Override
    public Customer get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(Customer data, AgencyAccountDetails account)
    {
        data.setId(IdGen.uuid());
        data.setDeleted(false);
        data.setAgencyId(account.getAgencyId());
        data.setCreatorName(account.getAccountName());
        data.setCreateTime(new Date());
        mapper.insert(data);
    }
    
    @Override
    @Transactional
    public void modify(Customer data)
    {
        mapper.update(data);
    }
    
    @Override
    public List<Customer> list(CustomerSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    public boolean isPhoneUnique(String id, String phone)
    {
        CustomerSearcher searcher = new CustomerSearcher();
        searcher.setCustomerPhone(phone);
        searcher.setPhoneExactMatches(true);
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }
    
    @Override
    public int customerCount(CustomerSearcher searcher)
    {
        return mapper.customerCount(searcher);
    }
    
    @Override
    public List<Customer> getCustomerByPhone(String phone)
    {
        return mapper.getCustomerByPhone(phone);
    }
}
