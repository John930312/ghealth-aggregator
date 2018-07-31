package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.mybatis.mapper.AgencyAccountMapper;
import com.todaysoft.ghealth.mybatis.mapper.UserMapper;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountDetails;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountToken;
import com.todaysoft.ghealth.mybatis.model.ManagementAccountDetails;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserAccountToken;
import com.todaysoft.ghealth.service.IAccountService;

@Service
public class AccountService implements IAccountService
{
    @Autowired
    private AgencyAccountMapper agencyAccountMapper;
    
    @Autowired
    private UserMapper userMapper;
    
    @Override
    public AgencyAccountDetails getAgencyAccountDetails(String token)
    {
        AgencyAccountToken accountToken = agencyAccountMapper.getAccountTokenRecordByToken(token);
        
        if (null == accountToken)
        {
            throw new ServiceException(ErrorCode.AGCY_LOGIN_ACCOUNT_TOKEN_INVALID);
        }
        
        AgencyAccount account = agencyAccountMapper.getAccountByAccountId(accountToken.getAccountId());
        
        if (null == account)
        {
            throw new ServiceException(ErrorCode.AGCY_LOGIN_ACCOUNT_TOKEN_INVALID);
        }
        
        AgencyAccountDetails details = new AgencyAccountDetails();
        details.setId(account.getId());
        details.setAgencyId(account.getAgencyId());
        details.setAccountName(account.getName());
        return details;
    }
    
    @Override
    public ManagementAccountDetails getManagementAccountDetails(String token)
    {
        UserAccountToken accountToken = userMapper.getAccountTokenRecordByToken(token);
        
        if (null == accountToken)
        {
            throw new ServiceException(ErrorCode.MGMT_LOGIN_ACCOUNT_TOKEN_INVALID);
        }
        
        User account = userMapper.get(accountToken.getAccountId());
        
        if (null == account)
        {
            throw new ServiceException(ErrorCode.MGMT_LOGIN_ACCOUNT_TOKEN_INVALID);
        }
        
        ManagementAccountDetails details = new ManagementAccountDetails();
        details.setId(account.getId());
        details.setName(account.getName());
        return details;
    }
}
