package com.todaysoft.ghealth.mybatis.model;

import java.util.List;

public class Gene extends PrimaryEntity
{
    private String id;
    
    private String symbol;
    
    private String name;
    
    private String description;
    
    private List<String> geneTitle;
    
    private List<GeneDocument> geneDocuments;
    
    @Override
    public String getId()
    {
        return id;
    }
    
    @Override
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
    
    public List<GeneDocument> getGeneDocuments()
    {
        return geneDocuments;
    }
    
    public void setGeneDocuments(List<GeneDocument> geneDocuments)
    {
        this.geneDocuments = geneDocuments;
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
