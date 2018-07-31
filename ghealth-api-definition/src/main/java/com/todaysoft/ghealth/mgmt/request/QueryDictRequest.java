package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryDictRequest extends SignatureTokenListRequest
{
    private String dictText;
    
    private String dictValue;
    
    private String category;
    
    public String getDictValue()
    {
        return dictValue;
    }
    
    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getDictText()
    {
        return dictText;
    }
    
    public void setDictText(String dictText)
    {
        this.dictText = dictText;
    }
}
