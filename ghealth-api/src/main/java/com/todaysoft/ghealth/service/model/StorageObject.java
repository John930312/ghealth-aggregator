package com.todaysoft.ghealth.service.model;

import java.io.IOException;

import com.todaysoft.ghealth.mybatis.model.ObjectStorage;

public interface StorageObject
{
    String getSuffix();
    
    byte[] getObjectContent()
        throws IOException;
    
    ObjectStorage toObjectStorageRecord();
}
