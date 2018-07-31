package com.todaysoft.ghealth.base.request;

import java.util.Map;

public class UsernamePasswordLoginRequest extends SignatureRequest
{
    private String username;
    
    private String password;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("username", username);
        fields.put("password", password);
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
}
