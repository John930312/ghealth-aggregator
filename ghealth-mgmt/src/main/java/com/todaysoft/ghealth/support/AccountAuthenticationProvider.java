package com.todaysoft.ghealth.support;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.AbstractUserDetailsAuthenticationProvider;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
public class AccountAuthenticationProvider extends AbstractUserDetailsAuthenticationProvider
{
    @Autowired
    private IMessageService messageService;
    
    @Autowired
    private IAuthenticationService authenticationService;
    
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException
    {
        
    }
    
    @Override
    protected UserDetails retrieveUser(String username, UsernamePasswordAuthenticationToken authentication)
        throws AuthenticationException
    {
        try
        {
            String password = (String)authentication.getCredentials();
            AccountDetails account = authenticationService.authenticate(username, password);
            return account;
        }
        catch (ServiceException e)
        {
            throw new BadCredentialsException(messageService.getMessage(e.getErrorCode()));
        }
    }
}
