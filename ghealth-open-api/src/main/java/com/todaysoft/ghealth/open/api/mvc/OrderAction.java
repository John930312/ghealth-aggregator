package com.todaysoft.ghealth.open.api.mvc;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hsgene.restful.response.DataResponse;
import com.hsgene.restful.util.CountRecords;
import com.todaysoft.ghealth.open.api.restful.model.OrderDTO;
import com.todaysoft.ghealth.open.api.restful.model.TestingItemReportDTO;
import com.todaysoft.ghealth.open.api.restful.request.OrderQueryRequest;
import com.todaysoft.ghealth.open.api.service.IOrderService;
import com.todaysoft.ghealth.open.api.service.credentials.CredentialsHolder;

@RestController
@RequestMapping("/open/orders")
public class OrderAction
{
    @Autowired
    private IOrderService orderService;
    
    @RequestMapping(value = "", method = RequestMethod.POST)
    public DataResponse<CountRecords<OrderDTO>> list(@RequestBody(required = false) OrderQueryRequest request, CredentialsHolder holder)
    {
        if (null == request)
        {
            request = new OrderQueryRequest();
            request.setLimit(10);
        }
        
        return orderService.list(request);
    }
    
    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public DataResponse<OrderDTO> get(@PathVariable String id, CredentialsHolder holder)
    {
        return orderService.get(id);
    }
    
    @RequestMapping(value = "/{id}/report/testing-items", method = RequestMethod.GET)
    public DataResponse<List<TestingItemReportDTO>> getTestingItemsReport(@PathVariable String id, CredentialsHolder holder)
    {
        return orderService.getTestingItemsReport(id);
    }
}
