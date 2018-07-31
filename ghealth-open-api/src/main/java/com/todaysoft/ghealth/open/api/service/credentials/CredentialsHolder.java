package com.todaysoft.ghealth.open.api.service.credentials;

import com.alibaba.druid.util.StringUtils;
import com.todaysoft.ghealth.open.api.mybatis.model.Credentials;

public class CredentialsHolder
{
    private Credentials credentials;
    
    public boolean isAdministrator()
    {
        if (null == credentials)
        {
            throw new IllegalStateException();
        }
        
        return StringUtils.isEmpty(credentials.getAgencyId());
    }
    
    public String getAgencyId()
    {
        if (null == credentials)
        {
            throw new IllegalStateException();
        }
        
        return credentials.getAgencyId();
    }
    
    public void setCredentials(Credentials credentials)
    {
        this.credentials = credentials;
    }
}
