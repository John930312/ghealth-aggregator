package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.model.Agency;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainCustomerRequest;
import com.todaysoft.ghealth.mgmt.request.QueryCustomersRequest;
import com.todaysoft.ghealth.model.customer.Customer;
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.service.wrapper.CustomerWrapper;
import com.todaysoft.ghealth.support.Pagination;

import java.util.List;

@Service
public class CustomerService implements ICustomerService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private CustomerWrapper customerWrapper;
    
    @Override
    public Pagination<Customer> searcher(CustomerSearcher searcher, int pageNo, int pageSize)
    {
        QueryCustomersRequest request = new QueryCustomersRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Customer> pagerResponse = gateway
            .request("/mgmt/customers/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Customer>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Customer> pager = pagerResponse.getData();
        Pagination<Customer> pagination = new Pagination<Customer>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(customerWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public Customer get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Customer> response = gateway
            .request("/mgmt/customers/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Customer>>()
            {
            });
        return customerWrapper.wrap(response.getData());
    }
    
    @Override
    public void modify(Customer data)
    {
        MaintainCustomerRequest request = new MaintainCustomerRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/customers/modify", request);
    }
    
    @Override
    public boolean delete(String id)
    {
        MaintainCustomerRequest request = new MaintainCustomerRequest();
        request.setId(id);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/customers/delete", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }

    @Override
    public List<Customer> list(CustomerSearcher searcher)
    {
        QueryCustomersRequest request = new QueryCustomersRequest();
        BeanUtils.copyProperties(searcher, request,"startCreateTime", "endStartTime");
        customerWrapper.searcherCustomerWarp(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.Customer> response =
                gateway.request("/mgmt/customers/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Customer>>()
                {
                });
        List<com.todaysoft.ghealth.base.response.model.Customer> records = response.getData();
        return customerWrapper.wrap(records);
    }
}
