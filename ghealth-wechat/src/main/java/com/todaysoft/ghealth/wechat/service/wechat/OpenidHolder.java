package com.todaysoft.ghealth.wechat.service.wechat;

public class OpenidHolder
{
    private String openid;

    private boolean redirect;

    private String redirectUrl;
    
    public String getOpenid()
    {
        return openid;
    }
    
    public void setOpenid(String openid)
    {
        this.openid = openid;
    }
    
    public boolean isRedirect()
    {
        return redirect;
    }
    
    public void setRedirect(boolean redirect)
    {
        this.redirect = redirect;
    }
    
    public String getRedirectUrl()
    {
        return redirectUrl;
    }
    
    public void setRedirectUrl(String redirectUrl)
    {
        this.redirectUrl = redirectUrl;
    }
}
