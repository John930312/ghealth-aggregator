package com.todaysoft.ghealth.mybatis.model;

public class TestingItemLocusEvaluateRecord
{
    private String id;
    
    private String orderId;
    
    private String testingItemId;
    
    private String locusId;
    
    private String genetype;
    
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
    
    public String getLocusId()
    {
        return locusId;
    }
    
    public void setLocusId(String locusId)
    {
        this.locusId = locusId;
    }
    
    public String getGenetype()
    {
        return genetype;
    }
    
    public void setGenetype(String genetype)
    {
        this.genetype = genetype;
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
