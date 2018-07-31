package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

import java.util.Map;

public class QueryBarcodeRequest extends SignatureTokenListRequest
{
    private String prefixCode;
    
    private String barCodeComplete;
    
    private String isFree;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("prefixCode", prefixCode);
        fields.put("barCodeComplete", barCodeComplete);
        fields.put("isFree", isFree);
    }
    
    public String getPrefixCode()
    {
        return prefixCode;
    }
    
    public void setPrefixCode(String prefixCode)
    {
        this.prefixCode = prefixCode;
    }
    
    public String getBarCodeComplete()
    {
        return barCodeComplete;
    }
    
    public void setBarCodeComplete(String barCodeComplete)
    {
        this.barCodeComplete = barCodeComplete;
    }
    
    public String getIsFree()
    {
        return isFree;
    }
    
    public void setIsFree(String isFree)
    {
        this.isFree = isFree;
    }
}
