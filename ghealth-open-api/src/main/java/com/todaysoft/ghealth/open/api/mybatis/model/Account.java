package com.todaysoft.ghealth.open.api.mybatis.model;

public class Account
{
    private String id;
    
    private String name;
    
    private String password;
    
    private boolean enabled;
    
    private boolean agency;
    
    private String agencyId;
    
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
    
    public String getPassword()
    {
        return password;
    }
    
    public void setPassword(String password)
    {
        this.password = password;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public boolean isAgency()
    {
        return agency;
    }
    
    public void setAgency(boolean agency)
    {
        this.agency = agency;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
}
