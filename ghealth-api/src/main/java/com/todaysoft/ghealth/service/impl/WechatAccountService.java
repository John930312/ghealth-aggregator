package com.todaysoft.ghealth.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.todaysoft.ghealth.portal.wechat.WechatErrorCode;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.mapper.AccountDataMapper;
import com.todaysoft.ghealth.mybatis.model.AccountData;
import com.todaysoft.ghealth.mybatis.model.AccountToken;
import com.todaysoft.ghealth.mybatis.model.Customer;
import com.todaysoft.ghealth.mybatis.searcher.AccountDataSearcher;
import com.todaysoft.ghealth.mybatis.searcher.AccountTokenSearcher;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.service.IWechatAccountService;
import com.todaysoft.ghealth.service.IWechatTokenService;
import com.todaysoft.ghealth.utils.IdGen;
import com.todaysoft.ghealth.wechat.dto.WechatAccountDTO;
import com.todaysoft.ghealth.wechat.dto.WechatTokenDTO;

@Service
public class WechatAccountService implements IWechatAccountService
{
    @Autowired
    private AccountDataMapper mapper;
    
    @Autowired
    private ICustomerService customerService;
    
    @Autowired
    private IWechatTokenService tokenService;
    
    @Override
    public AccountData getAccountDataByAccountId(String accountId)
    {
        return mapper.getAccountById(accountId);
    }
    
    @Override
    @Transactional
    public WechatTokenDTO createAccount(WechatAccountDTO data)
    {
        WechatTokenDTO dto = new WechatTokenDTO();
        
        AccountDataSearcher searcher = new AccountDataSearcher();
        searcher.setPhone(data.getPhone());
        searcher.setOpenid(data.getOpenid());
        searcher.setEnabled(false);
        List<AccountData> accountDatas = mapper.findAccountData(searcher);
        if (!CollectionUtils.isEmpty(accountDatas))
        {
            if (accountDatas.size() > 1)
            {
                throw new ServiceException("openid为" + data.getOpenid() + "phone为" + data.getPhone() + "关联了多个数据");
            }
            
            AccountData accountData = accountDatas.get(0);
            
            searcher = new AccountDataSearcher();
            searcher.setOpenid(data.getOpenid());
            searcher.setEnabled(true);
            List<AccountData> enabledAccountDatas = mapper.findAccountData(searcher);
            if (!CollectionUtils.isEmpty(enabledAccountDatas))
            {
                enabledAccountDatas.stream().filter(x -> !x.getId().equals(accountData.getId())).forEach(x -> {
                    x.setEnabled(false);
                    x.setUpdatorName("ENCHAGED");
                    x.setUpdateTime(new Date());
                    mapper.modifyAccount(x);
                });
            }
            
            accountData.setEnabled(true);
            accountData.setUpdateTime(new Date());
            accountData.setUpdatorName("ENCHAGED");
            mapper.modifyAccount(accountData);
            
            AccountTokenSearcher accountTokenSearcher = new AccountTokenSearcher();
            accountTokenSearcher.setAccountId(accountData.getId());
            AccountToken accountToken = mapper.getAccountToken(accountTokenSearcher);
            if (Objects.isNull(accountToken))
            {
                throw new ServiceException("accountId" + accountData.getId() + "没有关联token");
            }
            
            dto.setToken(accountToken.getToken());
            return dto;
        }
        
        String name = null;
        List<Customer> customers = customerService.getCustomerByPhone(data.getPhone());
        if (!CollectionUtils.isEmpty(customers) && customers.size() == 1)
        {
            name = customers.get(0).getName();
        }
        
        Date now = new Date();
        String accountId = IdGen.uuid();
        AccountData accountData = new AccountData();
        accountData.setId(accountId);
        accountData.setOpenid(data.getOpenid());
        accountData.setPhone(data.getPhone());
        accountData.setEnabled(true);
        accountData.setName(name);
        accountData.setCreateTime(now);
        accountData.setDeleted(false);
        mapper.createAccount(accountData);
        //创建token表
        AccountToken accountToken = new AccountToken();
        accountToken.setId(IdGen.uuid());
        accountToken.setAccountId(accountId);
        String token = RandomStringUtils.randomAlphabetic(32);
        accountToken.setToken(token);
        accountToken.setCreateTime(now);
        accountToken.setExpireTime(DateUtils.addDays(now, 7));
        mapper.createAccountToken(accountToken);
        
        dto.setToken(token);
        return dto;
    }
    
    @Override
    public void modifyAccountToken(String openid)
    {
        Date now = new Date();
        AccountToken accountToken = tokenService.getTokenByOpenid(openid);
        accountToken.setCreateTime(now);
        accountToken.setExpireTime(DateUtils.addDays(now, 7));
        mapper.modifyAccountToken(accountToken);
    }
    
    @Override
    public String getAccountIdByToken(String token)
    {
        AccountTokenSearcher searcher = new AccountTokenSearcher();
        searcher.setToken(token);
        AccountToken accountToken = mapper.getAccountToken(searcher);
        if (Objects.isNull(accountToken))
        {
            throw new ServiceException("token为" + token + "没有关联accountId");
        }
        
        return accountToken.getAccountId();
    }
}
