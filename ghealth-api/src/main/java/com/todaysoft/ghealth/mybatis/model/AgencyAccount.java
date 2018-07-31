package com.todaysoft.ghealth.mybatis.model;

public class AgencyAccount extends PrimaryEntity
{

    
    private String agencyId;
    
    private String agencyCode;
    
    private String agencyAbbr;
    
    private String name;
    
    private String phone;
    
    private String username;
    
    private String password;
    
    private boolean enabled;
    
    private boolean primaryAccount;
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyCode()
    {
        return agencyCode;
    }
    
    public void setAgencyCode(String agencyCode)
    {
        this.agencyCode = agencyCode;
    }
    
    public String getAgencyAbbr()
    {
        return agencyAbbr;
    }
    
    public void setAgencyAbbr(String agencyAbbr)
    {
        this.agencyAbbr = agencyAbbr;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
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
    
    public boolean isPrimaryAccount()
    {
        return primaryAccount;
    }
    
    public void setPrimaryAccount(boolean primaryAccount)
    {
        this.primaryAccount = primaryAccount;
    }
    
}
