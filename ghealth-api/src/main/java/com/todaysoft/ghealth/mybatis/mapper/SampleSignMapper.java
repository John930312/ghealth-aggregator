package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.SampleSign;

public interface SampleSignMapper
{
    int create(SampleSign record);
    
    SampleSign getBySignRecordId(String id);
    
    int modify(SampleSign record);
    
}