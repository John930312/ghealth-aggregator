package com.todaysoft.ghealth.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.mapper.AccountDataMapper;
import com.todaysoft.ghealth.mybatis.model.AccountData;
import com.todaysoft.ghealth.mybatis.model.AccountToken;
import com.todaysoft.ghealth.mybatis.searcher.AccountDataSearcher;
import com.todaysoft.ghealth.mybatis.searcher.AccountTokenSearcher;
import com.todaysoft.ghealth.portal.wechat.WechatErrorCode;
import com.todaysoft.ghealth.service.IWechatTokenService;

@Service
public class WechatTokenService implements IWechatTokenService
{
    @Autowired
    private AccountDataMapper mapper;
    
    @Override
    public AccountToken getTokenByOpenid(String openid)
    {
        AccountDataSearcher accountDataSearcher = new AccountDataSearcher();
        accountDataSearcher.setOpenid(openid);
        accountDataSearcher.setEnabled(true);
        List<AccountData> accountDatas = mapper.findAccountData(accountDataSearcher);
        if (CollectionUtils.isEmpty(accountDatas))
        {
            return null;
        }
        
        if (accountDatas.size() > 1)
        {
            throw new ServiceException("openid为" + openid + "关联的多个数据");
        }
        String accountId = accountDatas.get(0).getId();
        AccountTokenSearcher accountTokenSearcher = new AccountTokenSearcher();
        accountTokenSearcher.setAccountId(accountId);
        
        return mapper.getAccountToken(accountTokenSearcher);
    }
    
    @Override
    public void setAccountDataDisabled(String phone)
    {
        AccountDataSearcher searcher = new AccountDataSearcher();
        searcher.setPhone(phone);
        searcher.setEnabled(true);
        List<AccountData> accountDatas = mapper.findAccountData(searcher);
        if (CollectionUtils.isEmpty(accountDatas) || accountDatas.size() > 1)
        {
            throw new ServiceException("phone为" + phone + "关联的数据异常");
        }
        AccountData accountData = accountDatas.get(0);
        accountData.setEnabled(false);
        accountData.setUpdateTime(new Date());
        accountData.setUpdatorName("ENCHAGED");
        mapper.modifyAccount(accountData);
    }
    
    @Override
    public void unbind(String phone)
    {
        AccountDataSearcher searcher = new AccountDataSearcher();
        searcher.setPhone(phone);
        List<AccountData> accountDatas = mapper.findAccountData(searcher);
        if (!CollectionUtils.isEmpty(accountDatas))
        {
            AccountData accountData = accountDatas.get(0);
            accountData.setDeleted(true);
            accountData.setDeletorName("管理员");
            accountData.setDeleteTime(new Date());
            mapper.modifyAccount(accountData);
        }
    }
    
    @Override
    public boolean existPhone(String phone)
    {
        AccountDataSearcher searcher = new AccountDataSearcher();
        searcher.setPhone(phone);
        return !CollectionUtils.isEmpty(mapper.findAccountData(searcher));
    }
}
