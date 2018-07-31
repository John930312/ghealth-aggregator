package com.todaysoft.ghealth.wechat.model;

import java.util.List;


public class ProductDetails extends Product
{
    private List<TestingItem> testingItems;
    
    public List<TestingItem> getTestingItems()
    {
        return testingItems;
    }
    
    public void setTestingItems(List<TestingItem> testingItems)
    {
        this.testingItems = testingItems;
    }
}
