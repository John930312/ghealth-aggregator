package com.todaysoft.ghealth.service.wrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mgmt.request.QuerySignInHistoryRequest;
import com.todaysoft.ghealth.model.signInHistory.SignInHistory;
import com.todaysoft.ghealth.model.signInHistory.SignInHistorySearcher;

@Component
public class SignInHistoryWrapper
{
    @Autowired
    private OrderWrapper wrapper;
    
    public List<SignInHistory> wrap(List<com.todaysoft.ghealth.base.response.model.SignInHistory> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        SignInHistory signInHistory;
        List<SignInHistory> signInHistories = new ArrayList<SignInHistory>();
        for (com.todaysoft.ghealth.base.response.model.SignInHistory record : records)
        {
            signInHistory = new SignInHistory();
            wrapRecord(record, signInHistory);
            signInHistories.add(signInHistory);
        }
        return signInHistories;
    }
    
    public SignInHistory wrap(com.todaysoft.ghealth.base.response.model.SignInHistory record)
    {
        if (null == record)
        {
            return null;
        }
        
        SignInHistory signInHistory = new SignInHistory();
        wrapRecord(record, signInHistory);
        return signInHistory;
    }
    
    private void wrapRecord(com.todaysoft.ghealth.base.response.model.SignInHistory source, SignInHistory target)
    {
        if (null != source.getOrder())
        {
            target.setOrder(wrapper.wrap(source.getOrder()));
        }
        BeanUtils.copyProperties(source, target);
    }
    
    public QuerySignInHistoryRequest wrapperSearcher(SignInHistorySearcher searcher)
    {
        QuerySignInHistoryRequest request = new QuerySignInHistoryRequest();
        BeanUtils.copyProperties(searcher, request, "startTime", "endtime");
        
        if (null != searcher.getStartTime())
        {
            request.setStartTime(searcher.getStartTime().getTime());
        }
        
        if (null != searcher.getEndTime())
        {
            request.setEndTime(searcher.getEndTime().getTime());
        }
        
        return request;
    }
}
