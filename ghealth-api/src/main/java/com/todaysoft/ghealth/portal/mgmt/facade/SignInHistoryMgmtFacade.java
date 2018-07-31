package com.todaysoft.ghealth.portal.mgmt.facade;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.base.response.Pager;
import com.todaysoft.ghealth.base.response.PagerResponse;
import com.todaysoft.ghealth.base.response.model.SignInHistory;
import com.todaysoft.ghealth.mgmt.request.QuerySignInHistoryRequest;
import com.todaysoft.ghealth.mgmt.request.QuerySignInOrderDetailRequest;
import com.todaysoft.ghealth.mybatis.searcher.SignInHistorySearcher;
import com.todaysoft.ghealth.service.ISignInHistoryService;
import com.todaysoft.ghealth.service.ISignInOrderDetailService;
import com.todaysoft.ghealth.service.impl.PagerQueryer;
import com.todaysoft.ghealth.service.wrapper.SignInHistoryWrapper;

@Component
public class SignInHistoryMgmtFacade
{
    @Autowired
    private SignInHistoryWrapper wrapper;
    
    @Autowired
    private ISignInHistoryService service;
    
    @Autowired
    private ISignInOrderDetailService signInOrderDetailService;
    
    public PagerResponse<SignInHistory> pager(@RequestBody QuerySignInHistoryRequest request)
    {
        int pageNo = null == request.getPageNo() ? 1 : request.getPageNo();
        int pageSize = null == request.getPageSize() ? 10 : request.getPageSize();
        
        SignInHistorySearcher searcher = new SignInHistorySearcher();
        wrapper.wrapperSearcher(searcher, request);
        PagerQueryer<com.todaysoft.ghealth.mybatis.model.SignInHistory> queryer = new PagerQueryer<com.todaysoft.ghealth.mybatis.model.SignInHistory>(service);
        Pager<com.todaysoft.ghealth.mybatis.model.SignInHistory> pager = queryer.query(searcher, pageNo, pageSize);
        Pager<SignInHistory> result = Pager.generate(pager.getPageNo(), pager.getPageSize(), pager.getTotalCount(), wrapper.wrap(pager.getRecords()));
        return new PagerResponse<SignInHistory>(result);
    }
    
    public ListResponse<SignInHistory> getSearch(QuerySignInOrderDetailRequest request)
    {
        List<com.todaysoft.ghealth.mybatis.model.SignInHistory> signInHistories = signInOrderDetailService.getSearch(request.getId());
        return new ListResponse<SignInHistory>(wrapper.wrap(signInHistories));
    }
}
