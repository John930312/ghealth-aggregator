package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.AccountToken;

public interface IWechatTokenService
{
    AccountToken getTokenByOpenid(String openid);
    
    void setAccountDataDisabled(String phone);

    void unbind(String phone);

    boolean existPhone(String phone);
}
