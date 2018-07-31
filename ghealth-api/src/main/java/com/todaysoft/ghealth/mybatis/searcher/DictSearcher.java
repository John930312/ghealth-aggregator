package com.todaysoft.ghealth.mybatis.searcher;

public class DictSearcher
{
    private String dictText;
    
    private String category;
    
    private String dictValue;
    
    private Integer offset;
    
    private Integer limit;
    
    public String getDictText()
    {
        return dictText;
    }
    
    public void setDictText(String dictText)
    {
        this.dictText = dictText;
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
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getDictValue()
    {
        return dictValue;
    }
    
    public void setDictValue(String dictValue)
    {
        this.dictValue = dictValue;
    }
}
