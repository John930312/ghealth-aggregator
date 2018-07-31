package com.todaysoft.ghealth.wechat.service.core;

import org.springframework.stereotype.Component;

@Component
public class AccountMockConfig
{
    private boolean enabled;
    
    private Account account;
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public Account getAccount()
    {
        return account;
    }
    
    public void setAccount(Account account)
    {
        this.account = account;
    }
}
