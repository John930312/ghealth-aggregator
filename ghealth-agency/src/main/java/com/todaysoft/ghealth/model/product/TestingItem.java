package com.todaysoft.ghealth.model.product;

public class TestingItem
{
    private String id;
    
    private String code;
    
    private String name;
    
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public TestingItem()
    {
    }
    
    public TestingItem(String id, String code, String name)
    {
        this.id = id;
        this.code = code;
        this.name = name;
    }
}
