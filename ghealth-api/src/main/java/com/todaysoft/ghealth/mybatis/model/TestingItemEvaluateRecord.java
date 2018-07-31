package com.todaysoft.ghealth.mybatis.model;

public class TestingItemEvaluateRecord
{
    private String id;
    
    private String orderId;
    
    private String testingItemId;
    
    private Double evaluateValue;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
    
    public String getTestingItemId()
    {
        return testingItemId;
    }
    
    public void setTestingItemId(String testingItemId)
    {
        this.testingItemId = testingItemId;
    }
    
    public Double getEvaluateValue()
    {
        return evaluateValue;
    }
    
    public void setEvaluateValue(Double evaluateValue)
    {
        this.evaluateValue = evaluateValue;
    }
}
