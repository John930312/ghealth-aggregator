package com.todaysoft.ghealth.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.ItemLocusMapper;
import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;
import com.todaysoft.ghealth.mybatis.searcher.ItemLocusSearcher;
import com.todaysoft.ghealth.service.IItemLocusService;

@Service
public class ItemLocusService implements IItemLocusService
{
    @Autowired
    private ItemLocusMapper mapper;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof ItemLocusSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((ItemLocusSearcher)searcher);
    }
    
    @Override
    public List<TestingItemLocus> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof ItemLocusSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            ItemLocusSearcher tis = (ItemLocusSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((ItemLocusSearcher)searcher);
    }
    
    @Override
    public List<TestingItemLocus> list(ItemLocusSearcher searcher)
    {
        return mapper.search(searcher);
    }
    
    @Override
    public TestingItemLocus get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(TestingItemLocus data)
    {
        mapper.insert(data);
    }
    
    @Override
    @Transactional
    public void modify(TestingItemLocus data)
    {
        mapper.update(data);
    }
    
    @Override
    @Transactional
    public void delete(String id)
    {
        mapper.delete(id);
    }
    
    @Override
    public void deleteByItemId(String itemId)
    {
        mapper.deleteForTestingItem(itemId);
    }
}
