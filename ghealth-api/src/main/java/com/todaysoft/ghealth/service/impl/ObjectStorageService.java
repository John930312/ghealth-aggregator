package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.mybatis.mapper.ReportGenerateTaskMapper;
import com.todaysoft.ghealth.mybatis.model.ObjectStorage;
import com.todaysoft.ghealth.service.IObjectStorageService;

@Service
public class ObjectStorageService implements IObjectStorageService
{
    @Autowired
    private ReportGenerateTaskMapper reportGenerateTaskMapper;
    
    @Override
    public ObjectStorage get(String id)
    {
        return reportGenerateTaskMapper.getObjectStorageRecord(id);
    }
}
