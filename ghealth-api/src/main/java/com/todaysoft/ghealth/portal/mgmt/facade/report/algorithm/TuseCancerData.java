package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.annotation.JsonInclude;

public class TuseCancerData
{
    private String cancerName;
    
    private Double risk;
    
    private Integer sex;
    
    @JsonInclude
    private Integer level;
    
    private Double realRisk;
    
    private Double avgRisk;
    
    public String getCancerName()
    {
        return cancerName;
    }
    
    public void setCancerName(String cancerName)
    {
        this.cancerName = cancerName;
    }
    
    public Double getRisk()
    {
        return risk;
    }
    
    public void setRisk(Double risk)
    {
        this.risk = risk;
    }
    
    public Integer getLevel()
    {
        return level;
    }
    
    public void setLevel(Integer level)
    {
        this.level = level;
    }
    
    public Double getRealRisk()
    {
        return realRisk;
    }
    
    public void setRealRisk(Double realRisk)
    {
        this.realRisk = realRisk;
    }
    
    public Integer getSex()
    {
        return sex;
    }
    
    public void setSex(Integer sex)
    {
        this.sex = sex;
    }
    
    public Double getAvgRisk()
    {
        return avgRisk;
    }
    
    public void setAvgRisk(Double avgRisk)
    {
        this.avgRisk = avgRisk;
    }
}
