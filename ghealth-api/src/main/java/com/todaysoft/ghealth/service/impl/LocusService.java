package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.mapper.LocusMapper;
import com.todaysoft.ghealth.mybatis.model.Locus;
import com.todaysoft.ghealth.mybatis.searcher.LocusSearcher;
import com.todaysoft.ghealth.service.ILocusService;

@Service
public class LocusService implements ILocusService
{
    @Autowired
    private LocusMapper locusMapper;
    
    @Override
    public List<Locus> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof LocusSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            LocusSearcher tis = (LocusSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return locusMapper.search((LocusSearcher)searcher);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof LocusSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return locusMapper.count((LocusSearcher)searcher);
    }
    
    @Override
    public List<Locus> list(Set<String> names)
    {
        if (CollectionUtils.isEmpty(names))
        {
            return Collections.emptyList();
        }
        
        return locusMapper.getLocusByNames(names);
    }
    
    @Override
    public Locus get(String id)
    {
        return locusMapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(Locus data)
    {
        locusMapper.create(data);
    }
    
    @Override
    @Transactional
    public void modify(Locus data)
    {
        locusMapper.modify(data);
    }
    
    @Override
    public boolean isNameUnique(String id, String name)
    {
        LocusSearcher searcher = new LocusSearcher();
        searcher.setName(name);
        searcher.setNameExactMatches(true);
        
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        
        int count = locusMapper.count(searcher);
        return count == 0;
    }
}
