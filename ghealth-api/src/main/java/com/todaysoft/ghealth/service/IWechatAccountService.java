package com.todaysoft.ghealth.service;

import com.todaysoft.ghealth.mybatis.model.AccountData;
import com.todaysoft.ghealth.wechat.dto.WechatAccountDTO;
import com.todaysoft.ghealth.wechat.dto.WechatTokenDTO;

public interface IWechatAccountService
{
    AccountData getAccountDataByAccountId(String accountId);

    WechatTokenDTO createAccount(WechatAccountDTO data);

    void modifyAccountToken(String openid);

    String getAccountIdByToken(String token);
}
