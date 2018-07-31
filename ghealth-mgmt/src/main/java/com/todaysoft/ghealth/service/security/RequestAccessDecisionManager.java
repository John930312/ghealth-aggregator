package com.todaysoft.ghealth.service.security;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.FilterInvocation;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.support.AccountDetails;

@Component
public class RequestAccessDecisionManager implements AccessDecisionManager
{
    @Autowired
    private ResourceAuthorizedDecision decision;
    
    @Override
    public void decide(Authentication auth, Object object, Collection<ConfigAttribute> attributes)
        throws AccessDeniedException, InsufficientAuthenticationException
    {
        String contextPath = ((FilterInvocation)object).getRequest().getContextPath();
        String uri = ((FilterInvocation)object).getRequest().getRequestURI();
        uri = uri.replaceAll(contextPath,"");
        
        if (auth instanceof AnonymousAuthenticationToken)
        {
            throw new InsufficientAuthenticationException("Anonymous Denied");
        }
        
        Object principal = auth.getPrincipal();
        AccountDetails account = (AccountDetails)principal;
        boolean authorized = decision.isAuthorized(uri, account);
        
        if (!authorized)
        {
            throw new AccessDeniedException("Insufficient Denied");
        }
    }
    
    @Override
    public boolean supports(ConfigAttribute attribute)
    {
        return true;
    }
    
    @Override
    public boolean supports(Class<?> clazz)
    {
        return true;
    }
}