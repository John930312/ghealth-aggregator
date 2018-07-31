package com.todaysoft.ghealth.portal.mgmt.mvc;

import com.todaysoft.ghealth.base.response.ListResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.mgmt.request.MaintainCustomerRequest;
import com.todaysoft.ghealth.mgmt.request.QueryCustomersRequest;
import com.todaysoft.ghealth.portal.mgmt.facade.CustomerMgmtFacade;

@RestController
@RequestMapping("/mgmt/customers")
public class CustomerMgmtController
{
    @Autowired
    private CustomerMgmtFacade facade;
    
    @RequestMapping("/pager")
    public PagerResponse<Customer> pager(@RequestBody QueryCustomersRequest request)
    {
        return facade.pager(request);
    }
    
    @RequestMapping("/details")
    public ObjectResponse<Customer> details(@RequestBody QueryDetailsRequest request)
    {
        return facade.get(request);
    }
    
    @RequestMapping("/modify")
    public void modify(@RequestBody MaintainCustomerRequest request)
    {
        facade.modify(request);
    }
    
    @RequestMapping("/delete")
    public ObjectResponse<Boolean> delete(@RequestBody MaintainCustomerRequest request)
    {
        return facade.delete(request);
    }

    @RequestMapping("/list")
    public ListResponse<Customer> list(@RequestBody QueryCustomersRequest request)
    {
        return facade.list(request);
    }
}
