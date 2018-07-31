package com.todaysoft.ghealth.open.api.restful.model;

public class TestingItemReportDTO
{
    private String testingItemName;
    
    private String testingItemCategory;
    
    private Integer evaluatedGrade;
    
    private String evaluatedValue;
    
    private String referenceValue;
    
    private String relativeValue;
    
    public String getTestingItemName()
    {
        return testingItemName;
    }
    
    public void setTestingItemName(String testingItemName)
    {
        this.testingItemName = testingItemName;
    }
    
    public String getTestingItemCategory()
    {
        return testingItemCategory;
    }
    
    public void setTestingItemCategory(String testingItemCategory)
    {
        this.testingItemCategory = testingItemCategory;
    }
    
    public Integer getEvaluatedGrade()
    {
        return evaluatedGrade;
    }
    
    public void setEvaluatedGrade(Integer evaluatedGrade)
    {
        this.evaluatedGrade = evaluatedGrade;
    }
    
    public String getEvaluatedValue()
    {
        return evaluatedValue;
    }
    
    public void setEvaluatedValue(String evaluatedValue)
    {
        this.evaluatedValue = evaluatedValue;
    }
    
    public String getReferenceValue()
    {
        return referenceValue;
    }
    
    public void setReferenceValue(String referenceValue)
    {
        this.referenceValue = referenceValue;
    }
    
    public String getRelativeValue()
    {
        return relativeValue;
    }
    
    public void setRelativeValue(String relativeValue)
    {
        this.relativeValue = relativeValue;
    }
}
