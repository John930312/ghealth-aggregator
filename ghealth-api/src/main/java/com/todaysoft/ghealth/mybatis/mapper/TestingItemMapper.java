package com.todaysoft.ghealth.mybatis.mapper;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;

public interface TestingItemMapper
{
    int count(TestingItemSearcher searcher);
    
    List<TestingItem> search(TestingItemSearcher searcher);
    
    TestingItem get(String id);
    
    void insert(TestingItem item);
    
    void update(TestingItem itemPojo);
}
