package com.todaysoft.ghealth.mybatis.model;

public class OrderTestingData
{
    private String id;
    
    private String orderId;
    
    private String uploadRecordId;
    
    private String details;

    private boolean flag;
    
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
    
    public String getUploadRecordId()
    {
        return uploadRecordId;
    }
    
    public void setUploadRecordId(String uploadRecordId)
    {
        this.uploadRecordId = uploadRecordId;
    }
    
    public String getDetails()
    {
        return details;
    }
    
    public void setDetails(String details)
    {
        this.details = details;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }
}
