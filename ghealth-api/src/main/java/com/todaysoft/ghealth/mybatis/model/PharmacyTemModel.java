package com.todaysoft.ghealth.mybatis.model;

public class PharmacyTemModel
{
    private String locusName;
    
    private String result;
    
    private String dose;
    
    private Integer level;
    
    private String geneType;
    
    public String getLocusName()
    {
        return locusName;
    }
    
    public void setLocusName(String locusName)
    {
        this.locusName = locusName;
    }
    
    public String getResult()
    {
        return result;
    }
    
    public void setResult(String result)
    {
        this.result = result;
    }
    
    public String getDose()
    {
        return dose;
    }
    
    public void setDose(String dose)
    {
        this.dose = dose;
    }
    
    public Integer getLevel()
    {
        return level;
    }
    
    public void setLevel(Integer level)
    {
        this.level = level;
    }
    
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
    
    public PharmacyTemModel(String locusName, String geneType, String result, String dose, Integer level)
    {
        this.locusName = locusName;
        this.result = result;
        this.dose = dose;
        this.level = level;
        this.geneType = geneType;
    }
    
    public PharmacyTemModel()
    {
    }
}
