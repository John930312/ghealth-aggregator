package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryLocusRequest extends SignatureTokenListRequest
{
    
    private String name;
    
    private String geneName;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("name", name);
        fields.put("geneName", geneName);
        
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getGeneName()
    {
        return geneName;
    }
    
    public void setGeneName(String geneName)
    {
        this.geneName = geneName;
    }
}
