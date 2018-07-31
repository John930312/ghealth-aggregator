package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.dto.TestingItemLocusDTO;
import com.todaysoft.ghealth.mgmt.request.MaintainTestingItemLocusRequest;
import com.todaysoft.ghealth.mgmt.request.QueryItemLocusRequest;
import com.todaysoft.ghealth.model.itemLocus.ItemLocusSearcher;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocus;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocusForm;
import com.todaysoft.ghealth.service.IItemLocusService;
import com.todaysoft.ghealth.service.wrapper.TestingItemLocusWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class ItemLocusService implements IItemLocusService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private TestingItemLocusWrapper testingItemLocusWrapper;
    
    @Override
    public Pagination<TestingItemLocus> pagination(ItemLocusSearcher searcher, int pageNo, int pageSize)
    {
        QueryItemLocusRequest request = new QueryItemLocusRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<TestingItemLocusDTO> response = gateway.request("/mgmt/item-locus/pager", request, new ParameterizedTypeReference<PagerResponse<TestingItemLocusDTO>>()
        {
        });
        Pager<TestingItemLocusDTO> pager = response.getData();
        Pagination<TestingItemLocus> pagination = new Pagination<TestingItemLocus>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(testingItemLocusWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public TestingItemLocus get(String id)
    {
        MaintainTestingItemLocusRequest request = new MaintainTestingItemLocusRequest();
        request.setId(id);
        
        ObjectResponse<TestingItemLocusDTO> response = gateway.request("/mgmt/item-locus/details", request, new ParameterizedTypeReference<ObjectResponse<TestingItemLocusDTO>>()
        {
        });
        return testingItemLocusWrapper.wrap(response.getData());
    }
    
    @Override
    public void create(TestingItemLocusForm data)
    {
        MaintainTestingItemLocusRequest request = new MaintainTestingItemLocusRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/item-locus/create", request);
    }
    
    @Override
    public void modify(TestingItemLocusForm data)
    {
        MaintainTestingItemLocusRequest request = new MaintainTestingItemLocusRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/item-locus/modify", request);
    }
    
    @Override
    public void delete(String id)
    {
        MaintainTestingItemLocusRequest request = new MaintainTestingItemLocusRequest();
        request.setId(id);
        gateway.request("/mgmt/item-locus/delete", request);
    }
}
