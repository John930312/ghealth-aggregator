package com.todaysoft.ghealth.portal.mgmt.facade;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.mybatis.model.Order;
import com.todaysoft.ghealth.mybatis.searcher.OrderSearcher;
import com.todaysoft.ghealth.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.mgmt.request.MaintainCustomerRequest;
import com.todaysoft.ghealth.mgmt.request.QueryCustomersRequest;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.searcher.CustomerSearcher;
import com.todaysoft.ghealth.service.IAccountService;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.CustomerWrapper;

@Component
public class CustomerMgmtFacade
{
    @Autowired
    private CustomerWrapper customerWrapper;
    
    @Autowired
    private IAccountService accountService;
    
    @Autowired
    private ICustomerService service;

    @Autowired
    private IOrderService orderService;
    
    public PagerResponse<Customer> pager(@RequestBody QueryCustomersRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        CustomerSearcher searcher = new CustomerSearcher();
        searcher.setAgencyName(request.getAgencyName());
        searcher.setCustomerName(request.getCustomerName());
        searcher.setCustomerPhone(request.getCustomerPhone());
        searcher.setAgencyId(request.getAgencyId());
        searcher.setSex(request.getSex());
        wrapSearcher(request, searcher);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.Customer> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.Customer>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.Customer> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<Customer> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), customerWrapper.wrap(pager.getRecords()));
        return new PagerResponse<Customer>(result);
    }
    
    public ObjectResponse<Customer> get(QueryDetailsRequest request)
    {
        com.todaysoft.ghealth.mybatis.model.Customer customer = service.get(request.getId());
        Customer data = customerWrapper.wrap(customer);
        return new ObjectResponse<Customer>(data);
    }
    
    public void modify(MaintainCustomerRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        
        com.todaysoft.ghealth.mybatis.model.Customer customer = new com.todaysoft.ghealth.mybatis.model.Customer();
        BeanUtils.copyProperties(request, customer);
        customer.setUpdatorName(account.getName());
        customer.setUpdateTime(new Date());
        service.modify(customer);
    }
    
    public ObjectResponse<Boolean> delete(MaintainCustomerRequest request)
    {
        ManagementAccountDetails account = accountService.getManagementAccountDetails(request.getToken());
        
        if (null == account)
        {
            throw new IllegalStateException();
        }
        OrderSearcher searcher = new OrderSearcher();
        searcher.setCustomerId(request.getId());
        List<Order> orders = orderService.list(searcher);
        if(CollectionUtils.isEmpty(orders))
        {
            com.todaysoft.ghealth.mybatis.model.Customer customer = service.get(request.getId());
            customer.setDeleted(true);
            customer.setDeletorName(account.getName());
            customer.setDeleteTime(new Date());
            service.modify(customer);
            return new ObjectResponse<>(true);
        }
        else
        {
            return new ObjectResponse<>(false);
        }

    }


    public ListResponse<Customer> list(QueryCustomersRequest request)
    {
        CustomerSearcher searcher = new CustomerSearcher();
        searcher.setAgencyName(request.getAgencyName());
        searcher.setCustomerName(request.getCustomerName());
        searcher.setCustomerPhone(request.getCustomerPhone());
        searcher.setAgencyId(request.getAgencyId());
        wrapSearcher(request, searcher);
        return new ListResponse<Customer>(customerWrapper.wrap(service.list(searcher)));
    }

    private void wrapSearcher(QueryCustomersRequest request, CustomerSearcher searcher)
    {
        if (StringUtils.isNotEmpty(request.getEndStartTime()))
        {
            searcher.setEndStartTime(transferLongToDate("yyyy-MM-dd hh:mm:ss", Long.valueOf(request.getEndStartTime())));
        }
        if (StringUtils.isNotEmpty(request.getStartCreateTime()))
        {
            searcher.setStartCreateTime(transferLongToDate("yyyy-MM-dd hh:mm:ss", Long.valueOf(request.getStartCreateTime())));
        }
    }

    private String transferLongToDate(String dateFormat, Long millSec)
    {
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        Date date = new Date(millSec);
        return sdf.format(date);
    }


}
