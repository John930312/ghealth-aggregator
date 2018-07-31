package com.todaysoft.ghealth.mybatis.model;

import java.util.Date;

public class OrderHistory
{
    public static final String EVENT_ORDER_SUBMIT = "2";
    
    public static final String EVENT_SAMPLE_SIGN = "3";
    
    public static final String EVENT_SAMPLE_DELIVERY = "4";
    
    private String id;
    
    private String orderId;
    
    private String title;
    
    private String eventType;
    
    private String eventDetails;
    
    private Date eventTime;

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
    
    public Date getEventTime()
    {
        return eventTime;
    }
    
    public void setEventTime(Date eventTime)
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