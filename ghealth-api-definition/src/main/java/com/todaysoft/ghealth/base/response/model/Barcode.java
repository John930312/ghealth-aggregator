package com.todaysoft.ghealth.base.response.model;

public class Barcode
{
    private String id;
    
    private String prefixCode;
    
    private Integer code;
    
    private String barCodeComplete;
    
    private String isFree;
    
    private Integer isDelete;

    public String getId()
    {
        return id;
    }

    public void setId(String id)
    {
        this.id = id;
    }

    public Integer getCode()
    {
        return code;
    }

    public void setCode(Integer code)
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
}
