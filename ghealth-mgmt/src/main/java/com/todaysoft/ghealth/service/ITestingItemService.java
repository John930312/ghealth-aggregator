package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.model.item.TestingItem;
import com.todaysoft.ghealth.model.item.TestingItemForm;
import com.todaysoft.ghealth.model.item.TestingItemSearcher;
import com.todaysoft.ghealth.support.Pagination;

public interface ITestingItemService
{
    Pagination<TestingItem> pager(TestingItemSearcher searcher, int pageNo, int pageSize);
    
    List<TestingItem> list(TestingItemSearcher searcher);
    
    TestingItem get(String id);
    
    boolean isCodeUnique(String id, String code);
    
    void create(TestingItemForm request);
    
    void modify(TestingItemForm request);
    
    boolean delete(String id);
    
    boolean setIsEnabled(TestingItemForm data);
}
