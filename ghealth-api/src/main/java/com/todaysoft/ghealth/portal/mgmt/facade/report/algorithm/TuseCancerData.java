package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class TuseCancerData
{
    private String cancerName;
    
    private List<TuseCancerRisk> risk;
    
    private Integer sex;
    
    @JsonInclude
    private Integer level;
    
    private Double realRisk;
    
    private List<TuseCancerRisk> avgRisk;
    
    @JsonInclude
    private Double riskBySex;
    
    @JsonInclude
    private Double avgRiskBySex;
    
    public String getCancerName()
    {
        return cancerName;
    }
    
    public void setCancerName(String cancerName)
    {
        this.cancerName = cancerName;
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
    
    public List<TuseCancerRisk> getRisk()
    {
        return risk;
    }
    
    public void setRisk(List<TuseCancerRisk> risk)
    {
        this.risk = risk;
    }
    
    public List<TuseCancerRisk> getAvgRisk()
    {
        return avgRisk;
    }
    
    public void setAvgRisk(List<TuseCancerRisk> avgRisk)
    {
        this.avgRisk = avgRisk;
    }
    
    public Double getRiskBySex()
    {
        return riskBySex;
    }
    
    public void setRiskBySex(Double riskBySex)
    {
        this.riskBySex = riskBySex;
    }
    
    public Double getAvgRiskBySex()
    {
        return avgRiskBySex;
    }
    
    public void setAvgRiskBySex(Double avgRiskBySex)
    {
        this.avgRiskBySex = avgRiskBySex;
    }
}
