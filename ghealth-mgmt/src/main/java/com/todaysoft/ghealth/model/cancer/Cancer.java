package com.todaysoft.ghealth.model.cancer;

import java.util.Date;

public class Cancer
{
    private String id;
    
    private String name;
    
    private Double riskmale;
    
    private Double riskfemale;
    
    private String description;
    
    private Date createTime;
    
    private Date updateTime;
    
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
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Double getRiskmale()
    {
        return riskmale;
    }
    
    public void setRiskmale(Double riskmale)
    {
        this.riskmale = riskmale;
    }
    
    public Double getRiskfemale()
    {
        return riskfemale;
    }
    
    public void setRiskfemale(Double riskfemale)
    {
        this.riskfemale = riskfemale;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
}
