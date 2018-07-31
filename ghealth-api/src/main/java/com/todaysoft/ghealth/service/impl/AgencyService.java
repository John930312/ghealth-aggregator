package com.todaysoft.ghealth.service.impl;

import java.util.*;


import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.todaysoft.ghealth.mybatis.mapper.AgencyAccountMapper;
import com.todaysoft.ghealth.mybatis.mapper.AgencyMapper;
import com.todaysoft.ghealth.mybatis.model.Agency;
import com.todaysoft.ghealth.mybatis.model.AgencyAccount;
import com.todaysoft.ghealth.mybatis.model.AgencyAccountToken;
import com.todaysoft.ghealth.mybatis.searcher.AgencySearcher;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.model.AgencyAccountLoginDetails;
import com.todaysoft.ghealth.utils.IdGen;
import org.springframework.util.CollectionUtils;

@Service
public class AgencyService implements IAgencyService
{
    @Autowired
    private AgencyMapper mapper;
    
    @Autowired
    private AgencyAccountMapper agencyAccountMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof AgencySearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return mapper.count((AgencySearcher)searcher);
    }
    
    @Override
    public List<Agency> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof AgencySearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            AgencySearcher tis = (AgencySearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return mapper.search((AgencySearcher)searcher);
    }
    
    @Override
    public boolean isUsernameUnique(String username)
    {
        AgencySearcher searcher = new AgencySearcher();
        searcher.setPrimaryUsername(username);
        searcher.setPrimaryUsernameExactMatches(true);
        int count = mapper.count(searcher);
        return count == 0;
    }
    
    @Override
    public boolean isCodeUnique(String id, String code)
    {
        AgencySearcher searcher = new AgencySearcher();
        searcher.setCode(code);
        searcher.setCodeExactMatches(true);
        
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        
        int count = mapper.count(searcher);
        return count == 0;
    }
    
    @Override
    public Agency get(String id)
    {
        return mapper.get(id);
    }
    
    @Override
    @Transactional
    public void create(Agency data, AgencyAccount accountData)
    {
        mapper.insert(data);
        
        if (!StringUtils.isEmpty(accountData.getPassword()))
        {
            accountData.setPassword(passwordEncoder.encode(accountData.getPassword()));
        }
        
        agencyAccountMapper.insert(accountData);
    }
    
    @Override
    @Transactional
    public void modify(Agency data, AgencyAccount accountData)
    {
        mapper.update(data);
        
        if (null != accountData)
        {
            if (StringUtils.isNotEmpty(accountData.getPassword()))
            {
                accountData.setPassword(passwordEncoder.encode(accountData.getPassword()));
            }
            agencyAccountMapper.update(accountData);
        }
    }
    
    @Override
    public AgencyAccount getPrimaryAccount(String id)
    {
        return agencyAccountMapper.getAgencyPrimaryAccount(id);
    }

    @Override
    public List<String> getSubAccount(String id)
    {
        return mapper.getSubAccount(id);
    }

    @Override
    @Transactional
    public AgencyAccountLoginDetails login(String username, String password)
    {
        // 查询账号
        AgencyAccount account = agencyAccountMapper.getAccountByAccountUsername(username);


        if (null == account)
        {
            throw new ServiceException(ErrorCode.AGCY_LOGIN_ACCOUNT_NOT_EXISTS);
        }
        
        if (StringUtils.isEmpty(account.getPassword()) || !passwordEncoder.matches(password, account.getPassword()))
        {

           throw new ServiceException(ErrorCode.AGCY_LOGIN_PASSWORD_UNMATCHED);
        }
        
        if (!account.isEnabled())
        {
            throw new ServiceException(ErrorCode.AGCY_LOGIN_ACCOUNT_DISABLED);
        }
        
        // 登录成功，设置token
        Date timestamp = new Date();
        String token;
        
        AgencyAccountToken accountToken = agencyAccountMapper.getAccountTokenRecordByAccountId(account.getId());
        
        if (null == accountToken)
        {
            token = RandomStringUtils.randomAlphabetic(32);
            
            accountToken = new AgencyAccountToken();
            accountToken.setId(IdGen.uuid());
            accountToken.setAccountId(account.getId());
            accountToken.setToken(token);
            accountToken.setCreateTime(timestamp);
            accountToken.setExpireTime(DateUtils.addDays(timestamp, 7));
            agencyAccountMapper.insertAccountToken(accountToken);
        }
        else
        {
            token = accountToken.getToken();
            
            accountToken.setCreateTime(timestamp);
            accountToken.setExpireTime(DateUtils.addDays(timestamp, 7));
            agencyAccountMapper.updateAccountToken(accountToken);
        }
        
        AgencyAccountLoginDetails details = new AgencyAccountLoginDetails();
        details.setToken(token);
        details.setAccount(account);
        details.setAuthorizedResources(getUserAuthorizedResources(account));
        return details;
    }
    private Set<String> getUserAuthorizedResources(AgencyAccount account)
    {
        if (account.isPrimaryAccount())
        {
            return null;
        }

        List<String> authorizedResources = agencyAccountMapper.getAccountAuthorizedResources(account.getId());

        if (CollectionUtils.isEmpty(authorizedResources))
        {
            return Collections.emptySet();
        }

        return new HashSet<String>(authorizedResources);
    }

    
    @Override
    public List<Agency> list(AgencySearcher searcher)
    {
        return mapper.search(searcher);
    }

    @Override
    @Transactional
    public void modifyPassword(AgencyAccount accountData)
    {


        if (null != accountData)
        {
            if (StringUtils.isNotEmpty(accountData.getPassword()))
            {
                accountData.setPassword(passwordEncoder.encode(accountData.getPassword()));
            }
            agencyAccountMapper.updatePassword(accountData);
        }
    }


    @Override
    public AgencyAccount getAccount(String agencyId,String primaryUsername)
    {
        return agencyAccountMapper.getAccount(agencyId,primaryUsername);
    }


    @Override
    @Transactional
    public void modifyUserPassword(AgencyAccount accountData)
    {


        if (null != accountData)
        {
            if (StringUtils.isNotEmpty(accountData.getPassword()))
            {
                accountData.setPassword(passwordEncoder.encode(accountData.getPassword()));
            }
            agencyAccountMapper.updateUserPassword(accountData);
        }
    }

    @Override
    public Agency getReportData(String id)
    {
        return mapper.getReportData(id);
    }

}
