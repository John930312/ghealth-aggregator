package com.todaysoft.ghealth.wechat.dto;

public class WechatAccountDTO
{
    private String openid;
    
    private String phone;
    
    public String getOpenid()
    {
        return openid;
    }
    
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
}
