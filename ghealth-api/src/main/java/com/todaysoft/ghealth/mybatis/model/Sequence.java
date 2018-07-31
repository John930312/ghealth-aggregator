package com.todaysoft.ghealth.mybatis.model;

import java.util.Date;

public class Sequence
{
    private String id;
    
    private String name;
    
    private Integer currentValue;
    
    private Integer increment;
    
    private Date batchDate;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Integer getCurrentValue()
    {
        return currentValue;
    }
    
    public void setCurrentValue(Integer currentValue)
    {
        this.currentValue = currentValue;
    }
    
    public Integer getIncrement()
    {
        return increment;
    }
    
    public void setIncrement(Integer increment)
    {
        this.increment = increment;
    }
    
    public Date getBatchDate()
    {
        return batchDate;
    }
    
    public void setBatchDate(Date batchDate)
    {
        this.batchDate = batchDate;
    }
}