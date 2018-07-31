package com.todaysoft.ghealth.open.api.mybatis.model;

public class Product extends PrimaryEntity
{
    private String code;
    
    private String name;
    
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
}
