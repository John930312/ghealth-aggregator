package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;
import com.todaysoft.ghealth.mybatis.searcher.ItemLocusSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;

public interface IItemLocusService extends PagerQueryHandler<TestingItemLocus>
{
    List<TestingItemLocus> list(ItemLocusSearcher searcher);
    
    TestingItemLocus get(String id);
    
    void create(TestingItemLocus data);
    
    void modify(TestingItemLocus data);
    
    void delete(String id);
    
    void deleteByItemId(String itemId);
}
