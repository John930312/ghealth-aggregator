package com.todaysoft.ghealth.service.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;

import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.utils.JsonUtils;

public class LocalStorageObject implements StorageObject
{
    private String uri;
    
    private String path;
    
    public LocalStorageObject(String uri, String path)
    {
        this.uri = uri;
        this.path = path;
    }
    
    @Override
    public String getSuffix()
    {
        return this.path.substring(this.path.lastIndexOf("."));
    }
    
    @Override
    public byte[] getObjectContent()
        throws IOException
    {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        FileUtils.copyFile(new File(path), out);
        return out.toByteArray();
    }
    
    @Override
    public ObjectStorage toObjectStorageRecord()
    {
        Map<String, String> details = new HashMap<String, String>();
        details.put("uri", uri);
        
        ObjectStorage storage = new ObjectStorage();
        storage.setStorageType(ObjectStorage.STORAGE_LOCAL);
        storage.setStorageDetails(JsonUtils.toJson(details));
        return storage;
    }
}
