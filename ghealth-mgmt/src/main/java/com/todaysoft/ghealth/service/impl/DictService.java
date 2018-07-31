package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.request.QueryDetailsRequest;
import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.ObjectResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.MaintainDictRequest;
import com.todaysoft.ghealth.mgmt.request.QueryDictRequest;
import com.todaysoft.ghealth.model.dict.Dict;
import com.todaysoft.ghealth.model.dict.DictSearcher;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.service.wrapper.DictWrapper;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DictService implements IDictService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private DictWrapper wrapper;
    
    @Override
    public Pagination<Dict> searcher(DictSearcher searcher, int pageNo, int pageSize)
    {
        QueryDictRequest request = new QueryDictRequest();
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.Dict> pagerResponse =
            gateway.request("/mgmt/dict/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.Dict>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.Dict> pager = pagerResponse.getData();
        Pagination<Dict> pagination = new Pagination<Dict>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public Dict get(String id)
    {
        QueryDetailsRequest request = new QueryDetailsRequest();
        request.setId(id);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Dict> response =
            gateway.request("/mgmt/dict/details", request, new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Dict>>()
            {
            });
        return wrapper.wrap(response.getData());
    }
    
    @Override
    public void modify(Dict data)
    {
        MaintainDictRequest request = new MaintainDictRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/dict/modify", request);
    }
    
    @Override
    public void change(Dict data)
    {
        MaintainDictRequest request = new MaintainDictRequest();
        BeanUtils.copyProperties(data, request);
        gateway.request("/mgmt/dict/change", request);
    }
    
    @Override
    public List<Dict> getDictsByCategory(String category)
    {
        QueryDictRequest request = new QueryDictRequest();
        request.setCategory(category);
        ListResponse<com.todaysoft.ghealth.base.response.model.Dict> response = gateway
            .request("/mgmt/dict/getDictsByCategory", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.Dict>>()
            {
            });
        if (CollectionUtils.isEmpty(response.getData()))
        {
            return Collections.emptyList();
        }
        return wrapper.wrap(response.getData());
    }
    
    @Override
    public Dict getDictByCategoryAndValue(String category, String dictValue)
    {
        QueryDictRequest request = new QueryDictRequest();
        request.setCategory(category);
        request.setDictValue(dictValue);
        ObjectResponse<com.todaysoft.ghealth.base.response.model.Dict> response = gateway.request("/mgmt/dict/getDictByCategoryAndValue",
            request,
            new ParameterizedTypeReference<ObjectResponse<com.todaysoft.ghealth.base.response.model.Dict>>()
            {
            });
        if (null == response.getData())
        {
            return new Dict();
        }
        return wrapper.wrap(response.getData());
    }
}
