package com.todaysoft.ghealth.model.product;

import java.util.List;

import com.todaysoft.ghealth.model.item.TestingItem;

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
