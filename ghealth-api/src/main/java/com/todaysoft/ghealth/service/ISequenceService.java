package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.Sequence;

public interface ISequenceService
{
    Sequence get(String name);
    
    void setDefaultCurrentValue(String name);
    
    int getSerialNumber(String name);
    
    void updateBatchDate(String name);
    
    void setCurrentVal(Sequence data);
}
