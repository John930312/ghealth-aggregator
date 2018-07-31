package com.todaysoft.ghealth.base.response.model;

import java.util.Set;

public class AuthorizedAccountDetails
{
    private String token;
    
    private String name;
    
    private String username;
    
    private boolean builtin;
    
    private Set<String> authorizedResources;
    
    private String agencyId;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public boolean isBuiltin()
    {
        return builtin;
    }
    
    public void setBuiltin(boolean builtin)
    {
        this.builtin = builtin;
    }
    
    public Set<String> getAuthorizedResources()
    {
        return authorizedResources;
    }
    
    public void setAuthorizedResources(Set<String> authorizedResources)
    {
        this.authorizedResources = authorizedResources;
    }
}
