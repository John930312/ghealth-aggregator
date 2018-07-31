package com.todaysoft.ghealth.support;

public interface IAuthenticationService
{
    AccountDetails authenticate(String username, String password);
}
