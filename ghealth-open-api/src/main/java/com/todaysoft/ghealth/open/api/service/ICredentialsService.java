package com.todaysoft.ghealth.open.api.service;

import com.todaysoft.ghealth.open.api.mybatis.model.Credentials;

public interface ICredentialsService
{
    Credentials getCredentials(String key);
}
