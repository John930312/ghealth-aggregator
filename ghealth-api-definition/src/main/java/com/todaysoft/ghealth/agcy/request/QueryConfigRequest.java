package com.todaysoft.ghealth.agcy.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

import java.util.Map;

public class QueryConfigRequest extends SignatureTokenListRequest
{
    private String name;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("name", name);
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
}
