package com.todaysoft.ghealth.mybatis.model;

public class Area
{
    private String parentId;
    
    private String type;
    
    private String id;
    
    private String name;
    
    private String fullName;
    
    public String getParentId()
    {
        return parentId;
    }
    
    public void setParentId(String parentId)
    {
        this.parentId = parentId;
    }
    
    public String getType()
    {
        return type;
    }
    
    public void setType(String type)
    {
        this.type = type;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
}
