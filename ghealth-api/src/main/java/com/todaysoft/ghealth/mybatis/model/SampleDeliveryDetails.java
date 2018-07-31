package com.todaysoft.ghealth.mybatis.model;

public class SampleDeliveryDetails
{
    private String id;
    
    private String deliveryRecordId;
    
    private String orderId;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getDeliveryRecordId()
    {
        return deliveryRecordId;
    }
    
    public void setDeliveryRecordId(String deliveryRecordId)
    {
        this.deliveryRecordId = deliveryRecordId;
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