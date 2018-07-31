package com.todaysoft.ghealth.service.model;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import com.todaysoft.ghealth.config.RootContext;
import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.service.impl.core.AliyunOSSHandler;
import com.todaysoft.ghealth.utils.JsonUtils;

public class AliyunStorageObject implements StorageObject
{
    private String endpoint;
    
    private String bucketName;
    
    private String objectKey;
    
    public AliyunStorageObject(String endpoint, String bucketName, String objectKey)
    {
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.objectKey = objectKey;
    }
    
    @Override
    public String getSuffix()
    {
        return this.objectKey.substring(this.objectKey.lastIndexOf("."));
    }
    
    @Override
    public byte[] getObjectContent()
        throws IOException
    {
        AliyunOSSHandler handler = RootContext.getBean(AliyunOSSHandler.class);
        byte[] bytes = handler.download("http://" + endpoint, bucketName, objectKey);
        return bytes;
    }
    
    @Override
    public ObjectStorage toObjectStorageRecord()
    {
        Map<String, String> details = new HashMap<String, String>();
        details.put("endpoint", endpoint);
        details.put("bucketName", bucketName);
        details.put("objectKey", objectKey);
        
        ObjectStorage storage = new ObjectStorage();
        storage.setStorageType(ObjectStorage.STORAGE_ALI_OSS);
        storage.setStorageDetails(JsonUtils.toJson(details));
        return storage;
    }
}
