package com.todaysoft.ghealth.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.agcy.request.MaintainCustomerRequest;
import com.todaysoft.ghealth.agcy.request.QueryCustomersRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.model.customer.Customer;
import com.todaysoft.ghealth.model.customer.CustomerForm;
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class CustomerService implements ICustomerService
{
    @Autowired
    private Gateway gateway;
    
    @Override
    public Pagination<Customer> searcher(CustomerSearcher searcher, int pageNo, int pageSize)
    {
        QueryCustomersRequest request = new QueryCustomersRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Customer> response =
            gateway.request("/agcy/customer/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Customer>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Customer> pager = response.getData();
        
        Pagination<Customer> pagination = new Pagination<Customer>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        
        Customer customer;
        List<Customer> customers = new ArrayList<Customer>();
        
        for (com.todaysoft.ghealth.base.response.model.Customer record : pager.getRecords())
        {
            customer = new Customer();
            BeanUtils.copyProperties(record, customer, "createTime", "updateTime");
            customer.setCreateTime(null == record.getCreateTime() ? null : new Date(record.getCreateTime()));
            customer.setUpdateTime(null == record.getUpdateTime() ? null : new Date(record.getUpdateTime()));
            customers.add(customer);
        }
        
        pagination.setRecords(customers);
        return pagination;
    }
    
    @Override
    public List<Customer> list(CustomerSearcher searcher)
    {
        QueryCustomersRequest request = new QueryCustomersRequest();
        BeanUtils.copyProperties(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.Customer> response =
            gateway.request("/agcy/customer/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Customer>>()
            {
            });
        List<com.todaysoft.ghealth.base.response.model.Customer> datas = response.getData();
        
        List<Customer> customers = new ArrayList<Customer>();
        
        if (CollectionUtils.isEmpty(datas))
        {
            return customers;
        }
        
        Customer customer;
        for (com.todaysoft.ghealth.base.response.model.Customer data : datas)
        {
            customer = new Customer();
            
            BeanUtils.copyProperties(data, customer, "createTime", "updateTime");
            customer.setCreateTime(null == data.getCreateTime() ? null : new Date(data.getCreateTime()));
            customer.setUpdateTime(null == data.getUpdateTime() ? null : new Date(data.getUpdateTime()));
            customers.add(customer);
        }
        
        return customers;
    }
    
    @Override
    public Customer get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Customer> response =
            gateway.request("/agcy/customer/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Customer>>()
            {
            });
        
        if (null == response.getData())
        {
            return null;
        }
        
        Customer customer = new Customer();
        com.todaysoft.ghealth.base.response.model.Customer data = response.getData();
        BeanUtils.copyProperties(data, customer, "createTime", "updateTime");
        customer.setCreateTime(null == data.getCreateTime() ? null : new Date(data.getCreateTime()));
        customer.setUpdateTime(null == data.getUpdateTime() ? null : new Date(data.getUpdateTime()));
        return customer;
    }
    
    @Override
    public void create(CustomerForm data)
    {
        MaintainCustomerRequest request = new MaintainCustomerRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/customer/create", request);
    }
    
    @Override
    public void modify(CustomerForm data)
    {
        MaintainCustomerRequest request = new MaintainCustomerRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/agcy/customer/modify", request);
    }
    
    @Override
    public boolean delete(String id)
    {
        MaintainCustomerRequest request = new MaintainCustomerRequest();
        request.setId(id);
        ObjectResponse<Boolean> response =
                gateway.request("/agcy/customer/delete", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
                {
                });
        return response.getData();
    }

    @Override
    public boolean isPhoneUnique(String id, String phone)
    {
        MaintainCustomerRequest request = new MaintainCustomerRequest();
        request.setId(id);
        request.setPhone(phone);

        ObjectResponse<Boolean> response = gateway.request("/agcy/customer/unique/phone", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });

        if (null == response.getData())
        {
            return false;
        }

        return response.getData().booleanValue();
    }

}
