package com.todaysoft.ghealth.service.impl;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.mybatis.mapper.AgencyProductMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyProduct;
import com.todaysoft.ghealth.mybatis.searcher.AgencyProductSearcher;
import com.todaysoft.ghealth.service.IAgencyProductService;

@Service
public class AgencyProductService implements IAgencyProductService
{
    @Autowired
    private AgencyProductMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof AgencyProductSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((AgencyProductSearcher)searcher);
    }
    
    @Override
    public List<AgencyProduct> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof AgencyProductSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            AgencyProductSearcher tis = (AgencyProductSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((AgencyProductSearcher)searcher);
    }
    
    @Override
    public AgencyProduct get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    public void allocate(AgencyProduct data)
    {
        mapper.create(data);
    }
    
    @Override
    public void delete(AgencyProductSearcher searchder)
    {
        mapper.delete(searchder);
    }
    
    @Override
    public List<AgencyProduct> list(AgencyProductSearcher searchder)
    {
        return mapper.search(searchder);
    }
    
    @Override
    public void modify(AgencyProduct data)
    {
        mapper.modify(data);
    }
    
    @Override
    public String getIdByCode(String code, String agencyId)
    {
        return mapper.getIdByCode(code, agencyId);
    }

    @Override
    public AgencyProduct getBySearch(AgencyProductSearcher searchder)
    {
        return mapper.getBySearch(searchder);
    }


    
}
