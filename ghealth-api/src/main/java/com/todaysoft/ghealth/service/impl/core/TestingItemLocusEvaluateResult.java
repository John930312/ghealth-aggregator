package com.todaysoft.ghealth.service.impl.core;

import com.todaysoft.ghealth.mybatis.model.Locus;

public class TestingItemLocusEvaluateResult
{
    private Locus locus;
    
    private String genetype;
    
    private Double factor;
    
    public Locus getLocus()
    {
        return locus;
    }
    
    public void setLocus(Locus locus)
    {
        this.locus = locus;
    }
    
    public String getGenetype()
    {
        return genetype;
    }
    
    public void setGenetype(String genetype)
    {
        this.genetype = genetype;
    }
    
    public Double getFactor()
    {
        return factor;
    }
    
    public void setFactor(Double factor)
    {
        this.factor = factor;
    }
}
