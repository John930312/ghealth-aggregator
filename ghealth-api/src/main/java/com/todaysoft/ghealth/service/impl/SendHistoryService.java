package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.SendHistoryMapper;
import com.todaysoft.ghealth.mybatis.model.SignInHistory;
import com.todaysoft.ghealth.mybatis.searcher.SignInHistorySearcher;
import com.todaysoft.ghealth.service.ISendHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SendHistoryService implements ISendHistoryService{
    @Autowired(required = false)
    private SendHistoryMapper mapper;

    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof SignInHistorySearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((SignInHistorySearcher)searcher);
    }

    @Override
    public List<SignInHistory> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof SignInHistorySearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            SignInHistorySearcher tis = (SignInHistorySearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((SignInHistorySearcher)searcher);
    }
}
