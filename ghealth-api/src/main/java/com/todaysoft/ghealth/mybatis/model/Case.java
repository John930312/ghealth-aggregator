package com.todaysoft.ghealth.mybatis.model;

public class Case
{
    private Double evaluateValue;
    
    private int matchesValue;
    
    private int matchesCount;
    
    public Double getEvaluateValue()
    {
        return evaluateValue;
    }
    
    public void setEvaluateValue(Double evaluateValue)
    {
        this.evaluateValue = evaluateValue;
    }
    
    public int getMatchesValue()
    {
        return matchesValue;
    }
    
    public void setMatchesValue(int matchesValue)
    {
        this.matchesValue = matchesValue;
    }
    
    public int getMatchesCount()
    {
        return matchesCount;
    }
    
    public void setMatchesCount(int matchesCount)
    {
        this.matchesCount = matchesCount;
    }
}
