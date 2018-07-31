package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryItemLocusRequest extends SignatureTokenListRequest
{
    private String itemName;
    
    private String locusName;
    
    private String itemCode;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("itemName", itemName);
        fields.put("locusName", locusName);
        fields.put("itemCode", itemCode);
    }
    
    public String getItemName()
    {
        return itemName;
    }
    
    public void setItemName(String itemName)
    {
        this.itemName = itemName;
    }
    
    public String getLocusName()
    {
        return locusName;
    }
    
    public void setLocusName(String locusName)
    {
        this.locusName = locusName;
    }
    
    public String getItemCode()
    {
        return itemCode;
    }
    
    public void setItemCode(String itemCode)
    {
        this.itemCode = itemCode;
    }
}
