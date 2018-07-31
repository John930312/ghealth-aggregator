package com.todaysoft.ghealth.service.impl;

import com.todaysoft.ghealth.mybatis.mapper.AgencyProductOldPriceMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyProductOldPrice;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductOldPriceSearcher;
import com.todaysoft.ghealth.service.IAgencyProductOldPriceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AgencyProductOldPriceService implements IAgencyProductOldPriceService
{
    @Autowired
    private AgencyProductOldPriceMapper mapper;

    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof AgencyProductOldPriceSearcher))
        {
            throw new IllegalArgumentException();
        }

        return mapper.count((AgencyProductOldPriceSearcher)searcher);
    }

    @Override
    public List<AgencyProductOldPrice> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof AgencyProductOldPriceSearcher))
        {
            throw new IllegalArgumentException();
        }

        if (limit > 0)
        {
            AgencyProductOldPriceSearcher tis = (AgencyProductOldPriceSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }

        return mapper.search((AgencyProductOldPriceSearcher)searcher);
    }

    @Override
    @Transactional
    public void delete(AgencyProductOldPriceSearcher searchder)
    {
        mapper.delete(searchder);
    }

    @Override
    @Transactional
    public void create(AgencyProductOldPrice data)
    {
        mapper.insert(data);
    }

    @Override
    public List<AgencyProductOldPrice> list(AgencyProductOldPriceSearcher searcher)
    {
        return mapper.search(searcher);
    }

}
