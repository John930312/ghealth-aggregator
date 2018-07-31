package com.todaysoft.ghealth.base.response.model;

import java.util.List;

public class AgencyProductDetails extends AgencyProduct
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
