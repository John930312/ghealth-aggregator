package com.todaysoft.ghealth.mybatis.searcher;

/**
 * @Author: xjw
 * @Date: 2018/6/14 11:29
 */
public class AccountDataSearcher
{
    private String phone;
    
    private String openid;
    
    private boolean enabled;
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getOpenid()
    {
        return openid;
    }
    
    public void setOpenid(String openid)
    {
        this.openid = openid;
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
