package com.todaysoft.ghealth.mybatis.model;

public class SampleSignDetails
{
    private String id;
    
    private String signRecordId;
    
    private String orderId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSignRecordId()
    {
        return signRecordId;
    }
    
    public void setSignRecordId(String signRecordId)
    {
        this.signRecordId = signRecordId;
    }
    
    public String getOrderId()
    {
        return orderId;
    }
    
    public void setOrderId(String orderId)
    {
        this.orderId = orderId;
    }
}