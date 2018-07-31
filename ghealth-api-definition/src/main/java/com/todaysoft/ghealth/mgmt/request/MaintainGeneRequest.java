package com.todaysoft.ghealth.mgmt.request;

import java.util.List;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainGeneRequest extends SignatureTokenRequest
{
    private String id;
    
    private String symbol;
    
    private String name;
    
    private String description;
    
    private List<String> geneTitle;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public List<String> getGeneTitle()
    {
        return geneTitle;
    }
    
    public void setGeneTitle(List<String> geneTitle)
    {
        this.geneTitle = geneTitle;
    }
}
