package com.todaysoft.ghealth.base.response.model;

public class TemRequest
{
    private String geneType;
    
    private String male_factor;
    
    private String female_factor;
    
    public String getMale_factor()
    {
        return male_factor;
    }
    
    public void setMale_factor(String male_factor)
    {
        this.male_factor = male_factor;
    }
    
    public String getFemale_factor()
    {
        return female_factor;
    }
    
    public void setFemale_factor(String female_factor)
    {
        this.female_factor = female_factor;
    }
    
    public String getGeneType()
    {
        return geneType;
    }
    
    public void setGeneType(String geneType)
    {
        this.geneType = geneType;
    }
}
