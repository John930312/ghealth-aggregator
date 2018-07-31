package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.SignInHistoryMapper;
import com.todaysoft.ghealth.mybatis.model.SignInHistory;
import com.todaysoft.ghealth.mybatis.searcher.SignInHistorySearcher;
import com.todaysoft.ghealth.service.ISignInHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SignInHistoryService implements ISignInHistoryService {
    @Autowired(required = false)
    private SignInHistoryMapper mapper;

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
