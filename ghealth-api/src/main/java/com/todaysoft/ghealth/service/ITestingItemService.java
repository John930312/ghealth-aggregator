package com.todaysoft.ghealth.service;

import java.util.List;

import com.todaysoft.ghealth.mybatis.model.TestingItem;
import com.todaysoft.ghealth.mybatis.searcher.TestingItemSearcher;
import com.todaysoft.ghealth.service.impl.PagerQueryHandler;
import com.todaysoft.ghealth.service.impl.core.TestingItemEvaluateConfig;

public interface ITestingItemService extends PagerQueryHandler<TestingItem>
{
    List<TestingItem> list(TestingItemSearcher searcher);
    
    List<TestingItem> getItemsForProduct(String productId);
    
    TestingItemEvaluateConfig getTestingItemEvaluateConfig(TestingItem testingItem);
    
    TestingItem get(String id);
    
    void create(TestingItem data);
    
    void modify(TestingItem data);
    
    boolean isCodeUnique(String id, String code);
    
    boolean isRequiredSexForGenerate(List<TestingItem> testingItems);
}
