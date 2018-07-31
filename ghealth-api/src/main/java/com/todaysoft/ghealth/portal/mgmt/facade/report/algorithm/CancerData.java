package com.todaysoft.ghealth.portal.mgmt.facade.report.algorithm;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.List;

public class CancerData
{
    private String geneType;
    
    private List<TuseCancerData> value;

    @JsonInclude
    private String cancerFlag;
    
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    
    public List<TuseCancerData> getValue()
    {
        return value;
    }
    
    public void setValue(List<TuseCancerData> value)
    {
        this.value = value;
    }
    
    public String getCancerFlag()
    {
        return cancerFlag;
    }
    
    public void setCancerFlag(String cancerFlag)
    {
        this.cancerFlag = cancerFlag;
    }
}
