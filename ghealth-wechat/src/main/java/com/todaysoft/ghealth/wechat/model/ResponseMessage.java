package com.todaysoft.ghealth.wechat.model;

public class ResponseMessage
{
    private boolean canUse;
    
    private String message;
    
    public boolean isCanUse()
    {
        return canUse;
    }
    
    public void setCanUse(boolean canUse)
    {
        this.canUse = canUse;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
