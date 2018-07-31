package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.util.Map;

public class MaintainBarcodeRequest extends SignatureTokenRequest
{
    private String id;
    
    private String prefixCode;
    
    private String code;
    
    private String barCodeComplete;
    
    private String isFree;
    
    private Integer isDelete;
    
    private Integer count;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("code", code);
        fields.put("count", String.valueOf(count));
        fields.put("prefixCode", prefixCode);
        fields.put("barCodeComplete", barCodeComplete);
        fields.put("isFree", isFree);
        fields.put("isDelete", String.valueOf(isDelete));
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
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
    
    public Integer getIsDelete()
    {
        return isDelete;
    }
    
    public void setIsDelete(Integer isDelete)
    {
        this.isDelete = isDelete;
    }
    
    public Integer getCount()
    {
        return count;
    }
    
    public void setCount(Integer count)
    {
        this.count = count;
    }
}
