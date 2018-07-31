package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainTestingItemLocusRequest extends SignatureTokenRequest
{
    private String id;
    
    private String locusId;
    
    private String testingItemId;
    
    private String influenceFactors;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("locusId", locusId);
        fields.put("testingItemId", testingItemId);
        fields.put("influenceFactors", influenceFactors);
    }
    
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
    
    public String getTestingItemId()
    {
        return testingItemId;
    }
    
    public void setTestingItemId(String testingItemId)
    {
        this.testingItemId = testingItemId;
    }
    
    public String getInfluenceFactors()
    {
        return influenceFactors;
    }
    
    public void setInfluenceFactors(String influenceFactors)
    {
        this.influenceFactors = influenceFactors;
    }
}
