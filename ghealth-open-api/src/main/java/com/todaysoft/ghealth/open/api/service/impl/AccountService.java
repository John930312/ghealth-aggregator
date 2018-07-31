package com.todaysoft.ghealth.open.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.open.api.mybatis.mapper.AccountMapper;
import com.todaysoft.ghealth.open.api.mybatis.model.Account;
import com.todaysoft.ghealth.open.api.restful.model.AccountDTO;
import com.todaysoft.ghealth.open.api.service.IAccountService;
import com.todaysoft.ghealth.open.api.service.credentials.CredentialsHolder;
import com.todaysoft.ghealth.open.api.service.encoder.PasswordEncoder;
import com.todaysoft.ghealth.open.api.service.wrapper.AccountWrapper;

@Service
public class AccountService implements IAccountService
{
    @Autowired
    private AccountMapper accountMapper;
    
    @Autowired
    private AccountWrapper accountWrapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public DataResponse<AccountDTO> getMatchesAccount(String username, String password, CredentialsHolder holder)
    {
        // 优先查询代理商账号
        Account account = accountMapper.getAgencyAccount(username);
        
        if (null == account)
        {
            account = accountMapper.getManageAccount(username);
        }
        
        if (null == account)
        {
            return new DataResponse<AccountDTO>(null);
        }
        
        if (passwordEncoder.matches(password, account.getPassword()))
        {
            return new DataResponse<AccountDTO>(accountWrapper.wrap(account));
        }
        
        return new DataResponse<AccountDTO>(null);
    }
}
