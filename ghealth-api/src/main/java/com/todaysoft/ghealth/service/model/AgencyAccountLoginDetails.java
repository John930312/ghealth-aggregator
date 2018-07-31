package com.todaysoft.ghealth.service.model;

import com.todaysoft.ghealth.mybatis.model.AgencyAccount;

import java.util.Set;

public class AgencyAccountLoginDetails
{
    private String token;
    
    private AgencyAccount account;

    private Set<String> authorizedResources;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public AgencyAccount getAccount()
    {
        return account;
    }
    
    public void setAccount(AgencyAccount account)
    {
        this.account = account;
    }

    public Set<String> getAuthorizedResources() {
        return authorizedResources;
    }

    public void setAuthorizedResources(Set<String> authorizedResources) {
        this.authorizedResources = authorizedResources;
    }
}
