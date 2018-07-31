package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.GeneMapper;
import com.todaysoft.ghealth.mybatis.model.Gene;
import com.todaysoft.ghealth.mybatis.searcher.GeneSearcher;
import com.todaysoft.ghealth.service.IGeneService;

@Service
public class GeneService implements IGeneService
{
    @Autowired(required = false)
    private GeneMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof GeneSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((GeneSearcher)searcher);
    }
    
    @Override
    public List<Gene> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof GeneSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            GeneSearcher tis = (GeneSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((GeneSearcher)searcher);
    }
    
    @Override
    public Gene get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(Gene data)
    {
        mapper.insert(data);
    }
    
    @Override
    @Transactional
    public void modify(Gene data)
    {
        mapper.update(data);
    }

    @Override
    public boolean isNameUnique(String id, String name)
    {
        GeneSearcher searcher = new GeneSearcher();
        searcher.setName(name);
        searcher.setNameExactMatches(true);
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }

    @Override
    public boolean isSymbolUnique(String id, String symbol)
    {
        GeneSearcher searcher = new GeneSearcher();
        searcher.setSymbol(symbol);
        searcher.setSymbolExactMatches(true);
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        int count = mapper.count(searcher);
        return count == 0;
    }
}
