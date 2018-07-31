package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.model.itemLocus.ItemLocusSearcher;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocus;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocusForm;
import com.todaysoft.ghealth.support.Pagination;

public interface IItemLocusService
{
    Pagination<TestingItemLocus> pagination(ItemLocusSearcher searcher, int pageNo, int pageSize);
    
    TestingItemLocus get(String id);
    
    void create(TestingItemLocusForm data);
    
    void modify(TestingItemLocusForm data);
    
    void delete(String id);
    
}
