package com.todaysoft.ghealth.service.model.reportData;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.todaysoft.ghealth.mybatis.model.TestingItem;

import java.util.List;

public class ItemData
{
    @JsonIgnore
    private TestingItem testingItem;
    
    private String itemName;
    
    private String diseaseRisk;
    
    private String avgRisk;
    
    private String relativeRisk;
    
    private String itemResult;
    
    private String itemResultDesc;
    
    private List<LocusData> geneList;
    
    public String getItemName()
    {
        return itemName;
    }
    
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    
    public String getRelativeRisk()
    {
        return relativeRisk;
    }
    
    public void setRelativeRisk(String relativeRisk)
    {
        this.relativeRisk = relativeRisk;
    }
    
    public String getDiseaseRisk()
    {
        return diseaseRisk;
    }
    
    public void setDiseaseRisk(String diseaseRisk)
    {
        this.diseaseRisk = diseaseRisk;
    }
    
    public String getAvgRisk()
    {
        return avgRisk;
    }
    
    public void setAvgRisk(String avgRisk)
    {
        this.avgRisk = avgRisk;
    }
    
    public String getItemResult()
    {
        return itemResult;
    }
    
    public void setItemResult(String itemResult)
    {
        this.itemResult = itemResult;
    }
    
    public String getItemResultDesc()
    {
        return itemResultDesc;
    }
    
    public void setItemResultDesc(String itemResultDesc)
    {
        this.itemResultDesc = itemResultDesc;
    }
    
    public List<LocusData> getGeneList()
    {
        return geneList;
    }
    
    public void setGeneList(List<LocusData> geneList)
    {
        this.geneList = geneList;
    }
    
    public TestingItem getTestingItem()
    {
        return testingItem;
    }
    
    public void setTestingItem(TestingItem testingItem)
    {
        this.testingItem = testingItem;
    }
}
