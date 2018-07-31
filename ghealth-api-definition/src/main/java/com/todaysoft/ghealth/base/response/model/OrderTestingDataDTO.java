package com.todaysoft.ghealth.base.response.model;

public class OrderTestingDataDTO
{
    private String id;
    
    private String orderId;
    
    private String uploadRecordId;
    
    private String dataDetails;
    
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
    
    public String getDataDetails()
    {
        return dataDetails;
    }
    
    public void setDataDetails(String dataDetails)
    {
        this.dataDetails = dataDetails;
    }
}
