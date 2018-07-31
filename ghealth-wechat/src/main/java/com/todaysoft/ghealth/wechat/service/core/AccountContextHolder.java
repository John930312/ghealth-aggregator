package com.todaysoft.ghealth.wechat.service.core;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Component
public class AccountContextHolder
{
    private static final String ACCOUNT_KEY = "WECHAT_ACCOUNT";

    public Account getAccount()
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        Object account = attributes.getAttribute(ACCOUNT_KEY, RequestAttributes.SCOPE_SESSION);
        return (Account) account;
    }

    public void setAccount(Account account)
    {
        RequestAttributes attributes = RequestContextHolder.getRequestAttributes();
        attributes.setAttribute(ACCOUNT_KEY, account, RequestAttributes.SCOPE_SESSION);
    }
}
