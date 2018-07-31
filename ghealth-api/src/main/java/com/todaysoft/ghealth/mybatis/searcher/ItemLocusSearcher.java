package com.todaysoft.ghealth.mybatis.searcher;

public class ItemLocusSearcher
{
    private String itemId;
    
    private String itemName;
    
    private String locusName;
    
    private String itemCode;
    
    private Integer offset;
    
    private Integer limit;
    
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
    
    public String getItemId()
    {
        return itemId;
    }
    
    public void setItemId(String itemId)
    {
        this.itemId = itemId;
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
