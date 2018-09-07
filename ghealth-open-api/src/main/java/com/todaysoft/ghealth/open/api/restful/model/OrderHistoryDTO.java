package com.todaysoft.ghealth.open.api.restful.model;

public class OrderHistoryDTO
{
    private String id;
    
    private String orderId;
    
    private String title;
    
    private String eventType;
    
    private String eventDetails;
    
    private String eventTime;
    
    private String operatorName;
    
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
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getEventType()
    {
        return eventType;
    }
    
    public void setEventType(String eventType)
    {
        this.eventType = eventType;
    }
    
    public String getEventDetails()
    {
        return eventDetails;
    }
    
    public void setEventDetails(String eventDetails)
    {
        this.eventDetails = eventDetails;
    }
    
    public String getEventTime()
    {
        return eventTime;
    }
    
    public void setEventTime(String eventTime)
    {
        this.eventTime = eventTime;
    }
    
    public String getOperatorName()
    {
        return operatorName;
    }
    
    public void setOperatorName(String operatorName)
    {
        this.operatorName = operatorName;
    }
}