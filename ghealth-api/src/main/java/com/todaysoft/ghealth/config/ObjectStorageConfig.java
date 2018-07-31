package com.todaysoft.ghealth.config;

public class ObjectStorageConfig
{
    private String storageType;
    
    public boolean isAliyunOSS()
    {
        return "ALI-OSS".equalsIgnoreCase(storageType);
    }
    
    public String getStorageType()
    {
        return storageType;
    }
    
    public void setStorageType(String storageType)
    {
        this.storageType = storageType;
    }
}
