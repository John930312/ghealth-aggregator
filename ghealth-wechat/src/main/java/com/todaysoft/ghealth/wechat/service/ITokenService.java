package com.todaysoft.ghealth.wechat.service;

import com.todaysoft.ghealth.wechat.model.MobileUser;
import com.todaysoft.ghealth.wechat.model.Token;

public interface ITokenService
{
    Token getToken(String openid);
    
    Token active(MobileUser data);

    void freeze(String phone);

    void unbind(String phone);

    boolean existPhone(String phone);
}
