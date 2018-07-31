package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.CancerMapper;
import com.todaysoft.ghealth.mybatis.model.Cancer;
import com.todaysoft.ghealth.mybatis.searcher.CancerSearcher;
import com.todaysoft.ghealth.service.ICancerService;

@Service
public class CancerService implements ICancerService
{
    @Autowired(required = false)
    private CancerMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof CancerSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((CancerSearcher)searcher);
    }
    
    @Override
    public List<Cancer> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof CancerSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            CancerSearcher tis = (CancerSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((CancerSearcher)searcher);
    }
    
    @Override
    public Cancer get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void modify(Cancer data)
    {
        mapper.update(data);
    }
    
    @Override
    @Transactional
    public void create(Cancer data)
    {
        mapper.insert(data);
    }

    @Override
    public boolean isNameUnique(String id, String name)
    {
        CancerSearcher searcher = new CancerSearcher();
        searcher.setCancerName(name);
        searcher.setNameExactMatches(true);
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }


    
}
