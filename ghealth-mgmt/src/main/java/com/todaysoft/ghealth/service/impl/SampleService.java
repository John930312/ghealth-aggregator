package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainOrderRequest;
import com.todaysoft.ghealth.mgmt.request.MaintainSimpleRequest;
import com.todaysoft.ghealth.mgmt.request.QueryOrdersRequest;
import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.ISampleService;
import com.todaysoft.ghealth.service.IShortMessageService;
import com.todaysoft.ghealth.service.wrapper.OrderWrapper;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.utils.DataStatus;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

@Service
public class SampleService implements ISampleService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private OrderWrapper orderWrapper;
    
    @Autowired
    private IShortMessageService shortMessageService;
    
    @Autowired
    private IAgencyService agencyService;
    
    @Autowired
    private IOrderService orderService;
    
    private final Log logger = LogFactory.getLog(getClass());
    
    @Override
    public Pagination<Order> search(OrderSearcher searcher, int pageNo, int pageSize)
    {
        QueryOrdersRequest request = orderWrapper.searcherWarp(searcher);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Order> response =
            gateway.request("/mgmt/sample/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Order> pager = response.getData();
        
        Pagination<Order> pagination = new Pagination<Order>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        
        if (CollectionUtils.isEmpty(pager.getRecords()))
        {
            return pagination;
        }
        pagination.setRecords(orderWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public void modifyStatus(Order data)
    {
        MaintainSimpleRequest request = new MaintainSimpleRequest();
        request.setStatus(DataStatus.ORDER_COLLECTED);
        request.setOrderCodes(data.getOrderIds());
        gateway.request("/mgmt/sample/modifyStatus", request);
    }
    
    @Override
    public boolean status(Order data)
    {
        MaintainSimpleRequest request = new MaintainSimpleRequest();
        request.setIds(data.getOrderIds());
        ObjectResponse<Boolean> response = gateway.request("/mgmt/sample/status", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public Order getInformation(String orderCode)
    {
        QueryOrdersRequest request = new QueryOrdersRequest();
        request.setOrderCode(orderCode);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Order> response = gateway
            .request("/mgmt/sample/getInformation", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Order>>()
            {
            });
        return orderWrapper.wrap(response.getData());
    }
    
    @Override
    public void sampleSignedSend(Order data)
    {
        MaintainOrderRequest request = new MaintainOrderRequest();
        request.setOrderIds(data.getOrderIds());
        gateway.request("/mgmt/smsSend/sampleSignedSend", request);
    }
}
