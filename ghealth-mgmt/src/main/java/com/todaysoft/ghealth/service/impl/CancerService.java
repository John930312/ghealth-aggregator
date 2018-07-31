package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainCancerRequest;
import com.todaysoft.ghealth.mgmt.request.QueryCancersRequest;
import com.todaysoft.ghealth.model.cancer.Cancer;
import com.todaysoft.ghealth.model.cancer.CancerForm;
import com.todaysoft.ghealth.model.cancer.CancerSearcher;
import com.todaysoft.ghealth.service.ICancerService;
import com.todaysoft.ghealth.service.wrapper.CancerWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class CancerService implements ICancerService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private CancerWrapper cancerWrapper;
    
    @Override
    public Pagination<Cancer> searcher(CancerSearcher searcher, int pageNo, int pageSize)
    {
        QueryCancersRequest request = new QueryCancersRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Cancer> pagerResponse =
            gateway.request("/mgmt/cancer/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Cancer>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Cancer> pager = pagerResponse.getData();
        Pagination<Cancer> pagination = new Pagination<Cancer>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(cancerWrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public Cancer get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Cancer> response =
            gateway.request("/mgmt/cancer/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Cancer>>()
            {
            });
        return cancerWrapper.wrap(response.getData());
    }
    
    @Override
    public void delete(String id)
    {
        MaintainCancerRequest request = new MaintainCancerRequest();
        request.setId(id);
        gateway.request("/mgmt/cancer/delete", request);
        
    }
    
    @Override
    public void create(CancerForm data)
    {
        MaintainCancerRequest request = new MaintainCancerRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/cancer/create", request);
    }
    
    @Override
    public void modify(Cancer data)
    {
        MaintainCancerRequest request = new MaintainCancerRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/cancer/modify", request);
    }

    @Override
    public boolean isNameUnique(String id, String name)
    {
        MaintainCancerRequest request = new MaintainCancerRequest();
        request.setId(id);
        request.setName(name);

        ObjectResponse<Boolean> response = gateway.request("/mgmt/cancer/unique/name", request, new ParameterizedTypeReference<ObjectResponse<Boolean>>()
        {
        });

        if (null == response.getData())
        {
            return false;
        }

        return response.getData().booleanValue();
    }
    
}
