package com.todaysoft.ghealth.mybatis.model;

import java.util.List;

public class CaseMatchesAlgorithmDetails
{
    private List<Case> cases;
    
    private Double unmatchedEvaluateValue;
    
    public List<Case> getCases()
    {
        return cases;
    }
    
    public void setCases(List<Case> cases)
    {
        this.cases = cases;
    }
    
    public Double getUnmatchedEvaluateValue()
    {
        return unmatchedEvaluateValue;
    }
    
    public void setUnmatchedEvaluateValue(Double unmatchedEvaluateValue)
    {
        this.unmatchedEvaluateValue = unmatchedEvaluateValue;
    }
    
}
