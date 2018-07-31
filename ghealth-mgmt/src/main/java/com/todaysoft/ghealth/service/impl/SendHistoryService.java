package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.mgmt.request.QuerySignInHistoryRequest;
import com.todaysoft.ghealth.mgmt.request.QuerySignInOrderDetailRequest;
import com.todaysoft.ghealth.model.signInHistory.SignInHistory;
import com.todaysoft.ghealth.model.signInHistory.SignInHistorySearcher;
import com.todaysoft.ghealth.service.ISendHistoryService;
import com.todaysoft.ghealth.service.wrapper.SignInHistoryWrapper;
import com.todaysoft.ghealth.support.Pagination;

@Service
public class SendHistoryService implements ISendHistoryService
{
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private SignInHistoryWrapper wrapper;
    
    @Override
    public Pagination<SignInHistory> search(SignInHistorySearcher searcher, int pageNo, int pageSize)
    {
        QuerySignInHistoryRequest request = wrapper.wrapperSearcher(searcher);
        BeanUtils.copyProperties(searcher, request);
        request.setPageNo(pageNo);
        request.setPageSize(pageSize);
        PagerResponse<com.todaysoft.ghealth.base.response.model.SignInHistory> pagerResponse =
            gateway.request("/mgmt/sendHistory/pager", request, new ParameterizedTypeReference<PagerResponse<com.todaysoft.ghealth.base.response.model.SignInHistory>>()
            {
            });
        Pager<com.todaysoft.ghealth.base.response.model.SignInHistory> pager = pagerResponse.getData();
        Pagination<SignInHistory> pagination = new Pagination<SignInHistory>(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount());
        pagination.setRecords(wrapper.wrap(pager.getRecords()));
        return pagination;
    }
    
    @Override
    public List<SignInHistory> getSearch(String id)
    {
        QuerySignInOrderDetailRequest request = new QuerySignInOrderDetailRequest();
        request.setId(id);
        ListResponse<com.todaysoft.ghealth.base.response.model.SignInHistory> response =
            gateway.request("/mgmt/sendHistory/getSearch", request, new ParameterizedTypeReference<ListResponse<com.todaysoft.ghealth.base.response.model.SignInHistory>>()
            {
            });
        return wrapper.wrap(response.getData());
    }
}
