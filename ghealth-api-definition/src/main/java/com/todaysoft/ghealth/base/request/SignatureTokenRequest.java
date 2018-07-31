package com.todaysoft.ghealth.base.request;

import java.util.Map;

public abstract class SignatureTokenRequest extends SignatureRequest
{
    private String token;
    
    private boolean isLogin = true;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("token", token);
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public boolean isLogin()
    {
        return isLogin;
    }
    
    public void setLogin(boolean login)
    {
        isLogin = login;
    }
}
