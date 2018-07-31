package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingItemRequest;
import com.todaysoft.ghealth.mgmt.request.QueryTestingItemsRequest;
import com.todaysoft.ghealth.model.item.TestingItem;
import com.todaysoft.ghealth.model.item.TestingItemForm;
import com.todaysoft.ghealth.model.item.TestingItemSearcher;
import com.todaysoft.ghealth.service.ITestingItemService;
import com.todaysoft.ghealth.service.wrapper.TestingItemWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class TestingItemService implements ITestingItemService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private TestingItemWrapper testingItemWrapper;
    
    @Override
    public Pagination<TestingItem> pager(TestingItemSearcher searcher, int pageNo, int pageSize)
    {
        QueryTestingItemsRequest request = new QueryTestingItemsRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        
        PagerResponse<com.todaysoft.ghealth.base.response.model.TestingItem> response = gateway.request("/mgmt/testing-items/pager",
            request,
            new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.TestingItem>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.TestingItem> pager = response.getData();
        
        Pagination<TestingItem> pagination = new Pagination<TestingItem>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(testingItemWrapper.warp(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public List<TestingItem> list(TestingItemSearcher searcher)
    {
        QueryTestingItemsRequest request = new QueryTestingItemsRequest();
        BeanUtils.copyProperties(searcher, request);
        ListResponse<com.todaysoft.ghealth.base.response.model.TestingItem> response = gateway
            .request("/mgmt/testing-items/list", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.TestingItem>>()
            {
            });
        
        return testingItemWrapper.warp(response.getData());
    }
    
    @Override
    public TestingItem get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.TestingItem> response = gateway.request("/mgmt/testing-items/details",
            request,
            new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.TestingItem>>()
            {
            });
        return testingItemWrapper.entityToPageModel(response.getData());
    }
    
    @Override
    public boolean isCodeUnique(String id, String code)
    {
        MaintainTestingItemRequest request = new MaintainTestingItemRequest();
        request.setId(id);
        request.setCode(code);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/testing-items/unique/code", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        
        if (null == response.getData())
        {
            return false;
        }
        
        return response.getData().booleanValue();
    }
    
    @Override
    public void create(TestingItemForm data)
    {
        MaintainTestingItemRequest request = new MaintainTestingItemRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/testing-items/create", request);
    }
    
    @Override
    public void modify(TestingItemForm data)
    {
        MaintainTestingItemRequest request = new MaintainTestingItemRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/testing-items/modify", request);
    }
    
    @Override
    public boolean delete(String id)
    {
        MaintainTestingItemRequest request = new MaintainTestingItemRequest();
        request.setId(id);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/testing-items/delete", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
    
    @Override
    public boolean setIsEnabled(TestingItemForm data)
    {
        MaintainTestingItemRequest request = new MaintainTestingItemRequest();
        BeanUtils.copyProperties(data, request);
        ObjectResponse<Boolean> response = gateway.request("/mgmt/testing-items/setIsEnabled", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });
        return response.getData();
    }
}
