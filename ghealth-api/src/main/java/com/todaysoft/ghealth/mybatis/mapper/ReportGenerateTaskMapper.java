package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.mybatis.model.ReportGenerateTask;

public interface ReportGenerateTaskMapper
{
    ReportGenerateTask get(String id);
    
    void insert(ReportGenerateTask entity);
    
    void update(ReportGenerateTask entity);
    
    void delete(String id);
    
    void insertObjectStorageRecord(ObjectStorage entity);
    
    ObjectStorage getObjectStorageRecord(String id);

    void updateObjectStorageRecord(ObjectStorage entity);
}