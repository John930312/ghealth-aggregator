package com.todaysoft.ghealth.open.api.mybatis.model;

public class ObjectStorage
{
    public static final String STORAGE_LOCAL = "LOCAL";
    
    public static final String STORAGE_ALI_OSS = "ALI-OSS";
    
    private String id;
    
    private String storageType;
    
    private String storageDetails;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
    
    public String getStorageDetails()
    {
        return storageDetails;
    }
    
    public void setStorageDetails(String storageDetails)
    {
        this.storageDetails = storageDetails;
    }
}
