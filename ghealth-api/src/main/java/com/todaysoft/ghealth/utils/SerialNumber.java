package com.todaysoft.ghealth.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.mybatis.model.Sequence;
import com.todaysoft.ghealth.service.ISequenceService;

@Component
public class SerialNumber
{
    @Autowired
    private ISequenceService sequenceService;
    
    public String getCode(String name)
    {
        Sequence sequence = sequenceService.get(name);
        double distanceOfTwoDate = getDistanceOfTwoDate(sequence.getBatchDate(), new Date());
        if (distanceOfTwoDate > 0)
        {
            sequenceService.setDefaultCurrentValue(name);
            sequenceService.updateBatchDate(name);
        }
        Sequence newSequence = sequenceService.get(name);
        String prefixCode = new SimpleDateFormat("yyyyMMdd").format(new Date());
        Integer code = newSequence.getCurrentValue() + newSequence.getIncrement();
        Sequence data = new Sequence();
        data.setCurrentValue(code);
        data.setName(name);
        sequenceService.setCurrentVal(data);
        return prefixCode + String.format("%04d", code);
    }
    
    public double getDistanceOfTwoDate(Date before, Date after)
    {
        long beforeTime = before.getTime();
        long afterTime = after.getTime();
        return (afterTime - beforeTime) / (1000 * 60 * 60 * 24);
    }
    
}
