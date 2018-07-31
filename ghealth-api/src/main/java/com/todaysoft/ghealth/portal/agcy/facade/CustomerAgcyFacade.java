package com.todaysoft.ghealth.portal.agcy.facade;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.agcy.request.MaintainCustomerRequest;
import com.todaysoft.ghealth.agcy.request.QueryCustomersRequest;
import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.CustomerSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.CustomerWrapper;

@Component
public class CustomerAgcyFacade
{
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private CustomerWrapper customerWrapper;
    
    @Autowired
    private IOrderService orderService;
    
    public PagerResponse<Customer> pager(QueryCustomersRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        CustomerSearcher searcher = new CustomerSearcher();
        searcher.setAgencyId(account.getAgencyId());
        searcher.setCustomerName(request.getName());
        searcher.setCustomerPhone(request.getPhone());
        
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Customer> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Customer>(customerService);
        Pager<com.todaysoft.ghealth.mybatis.model.Customer> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Customer> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), customerWrapper.wrap(pager.getRecords()));
        return new PagerResponse<Customer>(result);
    }
    
    public ListResponse<Customer> list(QueryCustomersRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        CustomerSearcher searcher = new CustomerSearcher();
        searcher.setAgencyId(account.getAgencyId());
        searcher.setCustomerName(request.getName());
        searcher.setCustomerPhone(request.getPhone());
        
        List<com.todaysoft.ghealth.mybatis.model.Customer> records = customerService.query(searcher, 0, -1);
        
        if (CollectionUtils.isEmpty(records))
        {
            return new ListResponse<Customer>(Collections.emptyList());
        }
        else
        {
            return new ListResponse<Customer>(customerWrapper.wrap(records));
        }
    }
    
    public ObjectResponse<Customer> get(QueryDetailsRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Customer customer = customerService.get(request.getId());
        
        if (null == customer)
        {
            return new ObjectResponse<Customer>(null);
        }
        
        if (!account.getAgencyId().equals(customer.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        Customer details = customerWrapper.wrap(customer);
        return new ObjectResponse<Customer>(details);
    }
    
    public void create(MaintainCustomerRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Customer customer = new com.todaysoft.ghealth.mybatis.model.Customer();
        BeanUtils.copyProperties(request, customer);
        customerService.create(customer, account);
    }
    
    public void modify(MaintainCustomerRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Customer customer = new com.todaysoft.ghealth.mybatis.model.Customer();
        BeanUtils.copyProperties(request, customer);
        customer.setUpdatorName(account.getAccountName());
        customer.setUpdateTime(new Date());
        customerService.modify(customer);
    }
    
    public ObjectResponse<Boolean> delete(MaintainCustomerRequest request)
    {
        AgencyAccountDetails account = accountService.getAgencyAccountDetails(request.getToken());
        
        if (null == account || StringUtils.isEmpty(account.getAgencyId()))
        {
            throw new IllegalStateException();
        }
        OrderSearcher searcher = new OrderSearcher();
        searcher.setCustomerId(request.getId());
        List<Order> orders = orderService.list(searcher);
        if(CollectionUtils.isEmpty(orders))
        {
            com.todaysoft.ghealth.mybatis.model.Customer customer = customerService.get(request.getId());
            customer.setDeleted(true);
            customer.setDeletorName(account.getAccountName());
            customer.setDeleteTime(new Date());
            customerService.modify(customer);
            return new ObjectResponse<>(true);
        }
        else
        {
            return new ObjectResponse<>(false);
        }
    }
    
    public ObjectResponse<Boolean> isPhoneUnique(MaintainCustomerRequest request)
    {
        boolean unique = customerService.isPhoneUnique(request.getId(), request.getPhone());
        return new ObjectResponse<Boolean>(unique);
    }
}
