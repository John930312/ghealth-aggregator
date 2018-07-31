package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryGenesRequest extends SignatureTokenListRequest
{
    private String symbol;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("symbol", symbol);
    }
    
    public String getSymbol()
    {
        return symbol;
    }
    
    public void setSymbol(String symbol)
    {
        this.symbol = symbol;
    }
}
