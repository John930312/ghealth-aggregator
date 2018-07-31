package com.todaysoft.ghealth.open.api.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.open.api.mybatis.mapper.CredentialsMapper;
import com.todaysoft.ghealth.open.api.mybatis.model.Credentials;
import com.todaysoft.ghealth.open.api.service.ICredentialsService;

@Service
public class CredentialsService implements ICredentialsService
{
    @Autowired
    private CredentialsMapper mapper;
    
    @Override
    public Credentials getCredentials(String key)
    {
        return mapper.getCredentials(key);
    }
}
