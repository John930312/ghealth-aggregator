package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.base.response.ListResponse;
import com.todaysoft.ghealth.mybatis.mapper.AgencyBillMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyBill;
import com.todaysoft.ghealth.mybatis.searcher.AgencyBillSearcher;
import com.todaysoft.ghealth.service.IAgencyBillService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyBillService implements IAgencyBillService
{
    @Autowired
    private AgencyBillMapper mapper;
    
    @Override
    public void create(AgencyBill data)
    {
        mapper.create(data);
    }
    
    @Override
    public void modify(AgencyBill data)
    {
        mapper.update(data);
    }

    @Override
    public List<AgencyBill> list(AgencyBillSearcher searcher)
    {
        return mapper.search(searcher);
    }

    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof AgencyBillSearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((AgencyBillSearcher)searcher);
    }

    @Override
    public List<AgencyBill> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof AgencyBillSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            AgencyBillSearcher tis = (AgencyBillSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((AgencyBillSearcher)searcher);
    }
}
