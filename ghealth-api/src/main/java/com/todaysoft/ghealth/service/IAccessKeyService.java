package com.todaysoft.ghealth.service;

public interface IAccessKeyService
{
    boolean isValidKey(String key);
    
    String getSecret(String key);
}
