package com.todaysoft.ghealth.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.todaysoft.ghealth.service.IAccessKeyService;

@Service
public class AccessKeyService implements IAccessKeyService
{
    private Map<String, String> secrets = new HashMap<String, String>();
    
    @Override
    public boolean isValidKey(String key)
    {
        return secrets.containsKey(key);
    }
    
    @Override
    public String getSecret(String key)
    {
        return secrets.get(key);
    }
    
    @PostConstruct
    public void init()
    {
        secrets.put("DZuXmelufCfeuULbJIVlRNGulzbAbelt", "oWmKwqfSkqOgIrllFSOqIbwclcFxYssZ");
    }
}
