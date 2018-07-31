package com.todaysoft.ghealth.mybatis.mapper;

import com.todaysoft.ghealth.mybatis.model.Sequence;

public interface SequenceMapper
{
    Sequence get(String name);
    
    void setDefaultCurrentValue(String name);
    
    int getSerialNumber(String name);
    
    void updateBatchDate(String name);
    
    void setCurrentVal(Sequence sequence);
}