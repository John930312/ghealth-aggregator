package com.todaysoft.ghealth.open.api.restful.model;

public class AccountDTO
{
    private String id;
    
    private String name;
    
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
