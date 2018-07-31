package com.todaysoft.ghealth.service.model;

import java.util.Set;

import com.todaysoft.ghealth.mybatis.model.User;

public class UserAccountLoginDetails
{
    private String token;
    
    private User user;
    
    private Set<String> authorizedResources;
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public User getUser()
    {
        return user;
    }
    
    public void setUser(User user)
    {
        this.user = user;
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
