package com.todaysoft.ghealth.mybatis.model;

import com.todaysoft.ghealth.base.response.model.Authority;

public class RoleAuthority
{
    private Role role;
    
    private Authority authority;
    
    public Role getRole()
    {
        return role;
    }
    
    public void setRole(Role role)
    {
        this.role = role;
    }
    
    public Authority getAuthority()
    {
        return authority;
    }
    
    public void setAuthority(Authority authority)
    {
        this.authority = authority;
    }
}