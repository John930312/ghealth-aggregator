package com.todaysoft.ghealth.model.cancer;

public class CancerForm
{
    private String id;
    
    private String name;
    
    private Double riskmale;
    
    private Double riskfemale;
    
    private String description;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Double getRiskmale()
    {
        return riskmale;
    }
    
    public void setRiskmale(Double riskmale)
    {
        this.riskmale = riskmale;
    }
    
    public Double getRiskfemale()
    {
        return riskfemale;
    }
    
    public void setRiskfemale(Double riskfemale)
    {
        this.riskfemale = riskfemale;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
}
