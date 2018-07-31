package com.todaysoft.ghealth.support;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

public class AccountDetails implements UserDetails
{
    private static final long serialVersionUID = -3387979682204989700L;
    
    private String name;
    
    private String token;
    
    private String username;
    
    private String agencyId;

    private boolean primaryAccount;

    private Set<String> authorizedResources;
    
    public AccountDetails(String name, String username, String token, String agencyId)
    {
        this.name = name;
        this.username = username;
        this.token = token;
        this.agencyId = agencyId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getToken()
    {
        return token;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    @Override
    public String getUsername()
    {
        return username;
    }
    
    @Override
    public String getPassword()
    {
        return null;
    }
    
    @Override
    public boolean isEnabled()
    {
        return true;
    }
    
    @Override
    public boolean isAccountNonExpired()
    {
        return true;
    }
    
    @Override
    public boolean isAccountNonLocked()
    {
        return true;
    }
    
    @Override
    public boolean isCredentialsNonExpired()
    {
        return true;
    }
    
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities()
    {
        return Collections.emptySet();
    }

    public Set<String> getAuthorizedResources() {
        return authorizedResources;
    }

    public void setAuthorizedResources(Set<String> authorizedResources) {
        this.authorizedResources = authorizedResources;
    }

    public boolean isPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(boolean primaryAccount) {
        this.primaryAccount = primaryAccount;
    }
}
