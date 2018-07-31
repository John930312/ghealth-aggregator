package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.SignInOrderDetailMapper;
import com.todaysoft.ghealth.mybatis.model.SignInHistory;
import com.todaysoft.ghealth.mybatis.searcher.SignInHistorySearcher;
import com.todaysoft.ghealth.mybatis.searcher.SignInOrderDetailSearcher;
import com.todaysoft.ghealth.service.ISignInOrderDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class SignInOrderDetailService implements ISignInOrderDetailService{
    @Autowired(required = false)
    private SignInOrderDetailMapper mapper;
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof SignInOrderDetailSearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((SignInOrderDetailSearcher)searcher);
    }

    @Override
    public List<SignInHistory> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof SignInOrderDetailSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            SignInOrderDetailSearcher tis = (SignInOrderDetailSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((SignInOrderDetailSearcher)searcher);
    }

    @Override
    public List<SignInHistory> getSearch(String id){
        return mapper.getSearch(id);
    }
}
