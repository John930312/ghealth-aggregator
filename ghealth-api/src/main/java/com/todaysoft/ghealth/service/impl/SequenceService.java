package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.SequenceMapper;
import com.todaysoft.ghealth.mybatis.model.Sequence;
import com.todaysoft.ghealth.service.ISequenceService;

@Service
public class SequenceService implements ISequenceService
{
    @Autowired
    private SequenceMapper sequenceMapper;
    
    @Override
    public Sequence get(String name)
    {
        return sequenceMapper.get(name);
    }
    
    @Override
    @Transactional
    public void setDefaultCurrentValue(String name)
    {
        sequenceMapper.setDefaultCurrentValue(name);
    }
    
    @Override
    public int getSerialNumber(String name)
    {
        return sequenceMapper.getSerialNumber(name);
    }
    
    @Override
    @Transactional
    public void updateBatchDate(String name)
    {
        sequenceMapper.updateBatchDate(name);
    }
    
    @Override
    public void setCurrentVal(Sequence data)
    {
        sequenceMapper.setCurrentVal(data);
    }
}
