package com.todaysoft.ghealth.config;

public class AliyunOSSConfig
{
    private String endpoint;
    
    private String bucketName;
    
    private String directoryName;
    
    private String accessKeyId;
    
    private String accessKeySecret;
    
    private String callbackUrl;
    
    public String getEndpoint()
    {
        return endpoint;
    }
    
    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }
    
    public String getBucketName()
    {
        return bucketName;
    }
    
    public void setBucketName(String bucketName)
    {
        this.bucketName = bucketName;
    }
    
    public String getDirectoryName()
    {
        return directoryName;
    }
    
    public void setDirectoryName(String directoryName)
    {
        this.directoryName = directoryName;
    }
    
    public String getAccessKeyId()
    {
        return accessKeyId;
    }
    
    public void setAccessKeyId(String accessKeyId)
    {
        this.accessKeyId = accessKeyId;
    }
    
    public String getAccessKeySecret()
    {
        return accessKeySecret;
    }
    
    public void setAccessKeySecret(String accessKeySecret)
    {
        this.accessKeySecret = accessKeySecret;
    }
    
    public String getCallbackUrl()
    {
        return callbackUrl;
    }
    
    public void setCallbackUrl(String callbackUrl)
    {
        this.callbackUrl = callbackUrl;
    }
}
