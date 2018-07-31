package com.todaysoft.ghealth.agcy.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryAgencyProductsRequest extends SignatureTokenListRequest
{
    private String code;
    
    private String name;
    
    private String agencyId;
    
    private String agencyAccountId;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("code", code);
        fields.put("name", name);
        fields.put("agencyId", agencyId);
        fields.put("agencyAccountId", agencyAccountId);
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyAccountId()
    {
        return agencyAccountId;
    }
    
    public void setAgencyAccountId(String agencyAccountId)
    {
        this.agencyAccountId = agencyAccountId;
    }
}