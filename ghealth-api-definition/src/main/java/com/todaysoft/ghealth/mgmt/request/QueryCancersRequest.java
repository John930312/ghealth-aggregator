package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryCancersRequest extends SignatureTokenListRequest
{
    private String cancerName;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("cancerName", cancerName);
    }
    
    public String getCancerName()
    {
        return cancerName;
    }
    
    public void setCancerName(String cancerName)
    {
        this.cancerName = cancerName;
    }
}
