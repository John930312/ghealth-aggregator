package com.todaysoft.ghealth.open.api.mybatis.mapper;

import com.todaysoft.ghealth.open.api.mybatis.model.Credentials;

public interface CredentialsMapper
{
    Credentials getCredentials(String key);
}
