package com.todaysoft.ghealth.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.todaysoft.ghealth.mybatis.mapper.UserMapper;
import com.todaysoft.ghealth.mybatis.mapper.UserRoleMapper;
import com.todaysoft.ghealth.mybatis.model.Role;
import com.todaysoft.ghealth.mybatis.model.User;
import com.todaysoft.ghealth.mybatis.model.UserAccountToken;
import com.todaysoft.ghealth.mybatis.searcher.UserRoleSearcher;
import com.todaysoft.ghealth.mybatis.searcher.UserSearcher;
import com.todaysoft.ghealth.service.IUserService;
import com.todaysoft.ghealth.service.model.UserAccountLoginDetails;
import com.todaysoft.ghealth.utils.IdGen;

@Service
public class UserService implements IUserService
{
    @Autowired
    private UserMapper userMapper;
    
    @Autowired
    private PasswordEncoder passwordEncoder;
    
    @Autowired
    private UserRoleMapper userRoleMapper;
    
    @Override
    public void create(User data)
    {
        userMapper.insert(data);
    }
    
    @Override
    public User get(String id)
    {
        return userMapper.get(id);
    }
    
    @Override
    public void modify(User data)
    {
        userMapper.modify(data);
    }
    
    @Override
    public List<User> list(UserSearcher searcher)
    {
        return userMapper.search(searcher);
    }
    
    @Override
    public int count(Object searcher)
    {
        if (!(searcher instanceof UserSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        return userMapper.count((UserSearcher)searcher);
    }
    
    @Override
    public List<User> query(Object searcher, int offset, int limit)
    {
        if (!(searcher instanceof UserSearcher))
        {
            throw new IllegalArgumentException();
        }
        
        if (limit > 0)
        {
            UserSearcher tis = (UserSearcher)searcher;
            tis.setLimit(limit);
            tis.setOffset(offset < 0 ? 0 : offset);
        }
        
        return userMapper.search((UserSearcher)searcher);
    }
    
    @Override
    @Transactional
    public UserAccountLoginDetails login(String username, String password)
    {
        User user = userMapper.getUserByUsername(username);
        
        if (null == user)
        {
            throw new ServiceException(ErrorCode.MGMT_LOGIN_ACCOUNT_NOT_EXISTS);
        }
        
        if (StringUtils.isEmpty(user.getPassword()) || !passwordEncoder.matches(password, user.getPassword()))
        {
            throw new ServiceException(ErrorCode.MGMT_LOGIN_PASSWORD_UNMATCHED);
        }
        
        if (!user.isEnabled())
        {
            throw new ServiceException(ErrorCode.MGMT_LOGIN_ACCOUNT_DISABLED);
        }
        
        // 登录成功，设置token
        Date timestamp = new Date();
        String token;
        
        UserAccountToken accountToken = userMapper.getAccountTokenRecordByAccountId(user.getId());
        
        if (null == accountToken)
        {
            token = RandomStringUtils.randomAlphabetic(32);
            accountToken = new UserAccountToken();
            accountToken.setId(IdGen.uuid());
            accountToken.setAccountId(user.getId());
            accountToken.setToken(token);
            accountToken.setCreateTime(timestamp);
            accountToken.setExpireTime(DateUtils.addDays(timestamp, 7));
            userMapper.insertAccountToken(accountToken);
        }
        else
        {
            token = accountToken.getToken();
            accountToken.setCreateTime(timestamp);
            accountToken.setExpireTime(DateUtils.addDays(timestamp, 7));
            userMapper.updateAccountToken(accountToken);
        }
        
        UserAccountLoginDetails details = new UserAccountLoginDetails();
        details.setToken(token);
        details.setUser(user);
        details.setAuthorizedResources(getUserAuthorizedResources(user));
        return details;
    }
    
    private Set<String> getUserAuthorizedResources(User user)
    {
        if (user.isBuiltin())
        {
            return null;
        }
        
        List<String> authorizedResources = userMapper.getUserAuthorizedResources(user.getId());
        
        if (CollectionUtils.isEmpty(authorizedResources))
        {
            return Collections.emptySet();
        }
        
        return new HashSet<String>(authorizedResources);
    }
    
    @Override
    public List<Role> getRoles(UserRoleSearcher searcher)
    {
        return userRoleMapper.getRoles(searcher);
    }
    
    @Override
    public boolean isUsernameUnique(String username, String id)
    {
        UserSearcher searcher = new UserSearcher();
        searcher.setUsername(username);
        
        searcher.setUsernameExactMatches(true);
        if (!StringUtils.isEmpty(id))
        {
            searcher.setExcludeKeys(Collections.singleton(id));
        }
        int count = userMapper.count(searcher);
        return count == 0;
    }
}
