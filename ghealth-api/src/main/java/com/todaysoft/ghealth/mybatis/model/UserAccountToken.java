package com.todaysoft.ghealth.mybatis.model;

import java.util.Date;

public class UserAccountToken
{
    private String id;
    
    private String accountId;
    
    private String token;
    
    private Date createTime;
    
    private Date expireTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getAccountId()
    {
        return accountId;
    }
    
    public void setAccountId(String accountId)
    {
        this.accountId = accountId;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public void setToken(String token)
    {
        this.token = token;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getExpireTime()
    {
        return expireTime;
    }
    
    public void setExpireTime(Date expireTime)
    {
        this.expireTime = expireTime;
    }
}
