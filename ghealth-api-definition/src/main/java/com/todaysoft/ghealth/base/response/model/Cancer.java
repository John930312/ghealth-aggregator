package com.todaysoft.ghealth.base.response.model;

import java.math.BigDecimal;

public class Cancer
{
    private String id;
    
    private String name;
    
    private Double riskmale;
    
    private Double riskfemale;
    
    private String description;
    
    private Long createTime;
    
    private Long updateTime;
    
    private boolean deleted;
    
    private Long deleteTime;
    
    private String deletorName;
    
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
    
    public Long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }

    public Double getRiskmale() {
        return riskmale;
    }

    public void setRiskmale(Double riskmale) {
        this.riskmale = riskmale;
    }

    public Double getRiskfemale() {
        return riskfemale;
    }

    public void setRiskfemale(Double riskfemale) {
        this.riskfemale = riskfemale;
    }

    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Long getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Long deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getDeletorName()
    {
        return deletorName;
    }
    
    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }
    
    public Long getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Long updateTime)
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
