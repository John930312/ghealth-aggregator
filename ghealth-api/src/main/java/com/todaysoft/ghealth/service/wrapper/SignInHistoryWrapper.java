package com.todaysoft.ghealth.service.wrapper;

import com.todaysoft.ghealth.base.response.model.SignInHistory;
import com.todaysoft.ghealth.mgmt.request.QuerySignInHistoryRequest;
import com.todaysoft.ghealth.mybatis.searcher.SignInHistorySearcher;
import com.todaysoft.ghealth.service.ISignInHistoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Component
public class SignInHistoryWrapper {
    @Autowired
    private ISignInHistoryService service;

    @Autowired
    private OrderWrapper orderWrapper;

    public List<SignInHistory> wrap(List<com.todaysoft.ghealth.mybatis.model.SignInHistory> records)
    {
        if (CollectionUtils.isEmpty(records))
        {
            return Collections.emptyList();
        }
        SignInHistory signInHistory;
        List<SignInHistory> signInHistories = new ArrayList<SignInHistory>();
        for (com.todaysoft.ghealth.mybatis.model.SignInHistory record : records)
        {
            signInHistory = new SignInHistory();
            wrap(record, signInHistory);
            signInHistories.add(signInHistory);
        }

        return signInHistories;
    }

    public SignInHistory wrap(com.todaysoft.ghealth.mybatis.model.SignInHistory record)
    {
        SignInHistory signInHistory = new SignInHistory();
        wrap(record, signInHistory);
        return signInHistory;
    }

    private void wrap(com.todaysoft.ghealth.mybatis.model.SignInHistory source, SignInHistory target)
    {
        BeanUtils.copyProperties(source, target);
        if(null !=source.getOrder()){
            target.setOrder(orderWrapper.wrap(source.getOrder()));
        }
    }

    public void wrapperSearcher(SignInHistorySearcher searcher, QuerySignInHistoryRequest request)
    {
        BeanUtils.copyProperties(request, searcher);
        if (null != request.getStartTime())
        {
            searcher.setStartTime(new Date(request.getStartTime()));
        }
        if (null != request.getEndTime())
        {
            searcher.setEndTime(new Date(request.getEndTime()));
        }
    }
}
