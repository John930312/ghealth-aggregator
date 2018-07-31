package com.todaysoft.ghealth.base.response.dto;

import java.util.List;

import com.todaysoft.ghealth.mgmt.model.InfluenceFactorDTO;

public class TestingItemLocusDTO
{
    private String id;
    
    private String locusId;
    
    private String locusName;
    
    private String testingItemId;
    
    private String testingItemName;
    
    private String testingItemSexRestraint;
    
    private String testingItemCode;
    
    private List<InfluenceFactorDTO> influenceFactors;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getLocusId()
    {
        return locusId;
    }
    
    public void setLocusId(String locusId)
    {
        this.locusId = locusId;
    }
    
    public String getLocusName()
    {
        return locusName;
    }
    
    public void setLocusName(String locusName)
    {
        this.locusName = locusName;
    }
    
    public String getTestingItemId()
    {
        return testingItemId;
    }
    
    public void setTestingItemId(String testingItemId)
    {
        this.testingItemId = testingItemId;
    }
    
    public String getTestingItemName()
    {
        return testingItemName;
    }
    
    public void setTestingItemName(String testingItemName)
    {
        this.testingItemName = testingItemName;
    }
    
    public List<InfluenceFactorDTO> getInfluenceFactors()
    {
        return influenceFactors;
    }
    
    public void setInfluenceFactors(List<InfluenceFactorDTO> influenceFactors)
    {
        this.influenceFactors = influenceFactors;
    }
    
    public String getTestingItemSexRestraint()
    {
        return testingItemSexRestraint;
    }
    
    public void setTestingItemSexRestraint(String testingItemSexRestraint)
    {
        this.testingItemSexRestraint = testingItemSexRestraint;
    }
    
    public String getTestingItemCode()
    {
        return testingItemCode;
    }
    
    public void setTestingItemCode(String testingItemCode)
    {
        this.testingItemCode = testingItemCode;
    }
}
