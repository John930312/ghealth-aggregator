package com.todaysoft.ghealth.open.api.mybatis.model;

public class TestingItemEvaluation
{
    public static final String CATEGORY_DISEASE = "1";
    
    private String name;
    
    private String category;
    
    private String gradeConfig;
    
    private Double evaluatedValue;
    
    private Double maleReferenceValue;
    
    private Double femaleReferenceValue;
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getGradeConfig()
    {
        return gradeConfig;
    }
    
    public void setGradeConfig(String gradeConfig)
    {
        this.gradeConfig = gradeConfig;
    }
    
    public Double getEvaluatedValue()
    {
        return evaluatedValue;
    }
    
    public void setEvaluatedValue(Double evaluatedValue)
    {
        this.evaluatedValue = evaluatedValue;
    }
    
    public Double getMaleReferenceValue()
    {
        return maleReferenceValue;
    }
    
    public void setMaleReferenceValue(Double maleReferenceValue)
    {
        this.maleReferenceValue = maleReferenceValue;
    }
    
    public Double getFemaleReferenceValue()
    {
        return femaleReferenceValue;
    }
    
    public void setFemaleReferenceValue(Double femaleReferenceValue)
    {
        this.femaleReferenceValue = femaleReferenceValue;
    }
    
}
