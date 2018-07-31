package com.todaysoft.ghealth.support;

import java.util.List;

public class Menu
{
    private String id;
    
    private String name;
    
    private String uri;
    
    private String icon;
    
    private List<Menu> submenus;
    
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
    
    public String getUri()
    {
        return uri;
    }
    
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    
    public String getIcon()
    {
        return icon;
    }
    
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    
    public List<Menu> getSubmenus()
    {
        return submenus;
    }
    
    public void setSubmenus(List<Menu> submenus)
    {
        this.submenus = submenus;
    }
}
