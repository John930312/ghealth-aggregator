package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.TestingItemLocus;
import com.todaysoft.ghealth.mybatis.searcher.ItemLocusSearcher;

public interface ItemLocusMapper
{
    int count(ItemLocusSearcher searcher);
    
    List<TestingItemLocus> search(ItemLocusSearcher searcher);
    
    TestingItemLocus get(String id);
    
    void insert(TestingItemLocus data);
    
    void update(TestingItemLocus data);
    
    void delete(String id);
    
    void deleteForTestingItem(String itemId);
}