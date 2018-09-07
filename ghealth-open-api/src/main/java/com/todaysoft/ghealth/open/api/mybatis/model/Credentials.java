package com.todaysoft.ghealth.open.api.mybatis.model;

public class Credentials
{
    private String key;
    
    private String secret;
    
    private String agencyId;
    
    private boolean enabled;
    
    public String getKey()
    {
        return key;
    }
    
    public void setKey(String key)
    {
        this.key = key;
    }
    
    public String getSecret()
    {
        return secret;
    }
    
    public void setSecret(String secret)
    {
        this.secret = secret;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
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
