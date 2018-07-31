package com.todaysoft.ghealth.service.excel;

import org.apache.commons.lang3.StringUtils;

public class MenuResolveRecord
{
    private String firstLevelName;
    
    private String secondLevelName;
    
    private String thirdLevelName;
    
    private String icon;
    
    private String uri;
    
    private boolean enabled;
    
    public boolean isValid()
    {
        if (!StringUtils.isEmpty(firstLevelName))
        {
            return StringUtils.isEmpty(secondLevelName) && StringUtils.isEmpty(thirdLevelName);
        }
        
        if (!StringUtils.isEmpty(secondLevelName))
        {
            return StringUtils.isEmpty(firstLevelName) && StringUtils.isEmpty(thirdLevelName);
        }
        
        if (!StringUtils.isEmpty(thirdLevelName))
        {
            return StringUtils.isEmpty(firstLevelName) && StringUtils.isEmpty(secondLevelName);
        }
        
        return false;
    }
    
    public String getFirstLevelName()
    {
        return firstLevelName;
    }
    
    public void setFirstLevelName(String firstLevelName)
    {
        this.firstLevelName = firstLevelName;
    }
    
    public String getSecondLevelName()
    {
        return secondLevelName;
    }
    
    public void setSecondLevelName(String secondLevelName)
    {
        this.secondLevelName = secondLevelName;
    }
    
    public String getThirdLevelName()
    {
        return thirdLevelName;
    }
    
    public void setThirdLevelName(String thirdLevelName)
    {
        this.thirdLevelName = thirdLevelName;
    }
    
    public String getIcon()
    {
        return icon;
    }
    
    public void setIcon(String icon)
    {
        this.icon = icon;
    }
    
    public String getUri()
    {
        return uri;
    }
    
    public void setUri(String uri)
    {
        this.uri = uri;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
}
