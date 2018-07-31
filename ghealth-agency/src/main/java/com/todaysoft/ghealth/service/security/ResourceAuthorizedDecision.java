package com.todaysoft.ghealth.service.security;

import com.todaysoft.ghealth.support.AccountDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;

import java.util.Set;

@Component
public class ResourceAuthorizedDecision
{
    public boolean isAuthorized(String resource)
    {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        AccountDetails account = ((AccountDetails)authentication.getPrincipal());
        return isAuthorized(resource, account);
    }
    
    public boolean isAuthorized(String resource, AccountDetails account)
    {
        if (null == account)
        {
            return false;
        }
        
        AntPathMatcher matcher = new AntPathMatcher();
        
        if (matcher.match("/", resource)|| account.isPrimaryAccount())
        {
            return true;
        }
        
        Set<String> authorizedResources = account.getAuthorizedResources();
        
        if (CollectionUtils.isEmpty(authorizedResources))
        {
            return false;
        }
        
        for (String authorizedResource : authorizedResources)
        {
            if (matcher.match(authorizedResource, resource))
            {
                return true;
            }
        }
        
        return false;
    }
}
