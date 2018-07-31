package com.todaysoft.ghealth.wechat.service.wechat.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class SNSTokenDTO extends WechatDTO
{
    private String openid;
    
    private String scope;
    
    @JsonProperty("access_token")
    private String accessToken;
    
    @JsonProperty("refresh_token")
    private String refreshToken;
    
    @JsonProperty("expires_in")
    private String expires;
    
    public String getOpenid()
    {
        return openid;
    }
    
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    
    public String getScope()
    {
        return scope;
    }
    
    public void setScope(String scope)
    {
        this.scope = scope;
    }
    
    public String getAccessToken()
    {
        return accessToken;
    }
    
    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }
    
    public String getRefreshToken()
    {
        return refreshToken;
    }
    
    public void setRefreshToken(String refreshToken)
    {
        this.refreshToken = refreshToken;
    }
    
    public String getExpires()
    {
        return expires;
    }
    
    public void setExpires(String expires)
    {
        this.expires = expires;
    }
}
