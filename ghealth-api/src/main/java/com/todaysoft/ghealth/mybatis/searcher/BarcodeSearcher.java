package com.todaysoft.ghealth.mybatis.searcher;

public class BarcodeSearcher
{
    private String prefixCode;
    
    private String barCodeComplete;
    
    private String isFree;
    
    private Integer offset;
    
    private Integer limit;
    
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
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
}
