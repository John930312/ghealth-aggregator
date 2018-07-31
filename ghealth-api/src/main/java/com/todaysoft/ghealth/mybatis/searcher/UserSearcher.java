package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Set;

public class UserSearcher
{
    private String username;
    
    private boolean usernameExactMatches;

    private Set<String> excludeKeys;
    
    private String phone;
    
    private String name;
    
    private Boolean builtin;
    
    private Integer offset;
    
    private Integer limit;
    
    public String getUsername()
    {
        return username;
    }
    
    public void setUsername(String username)
    {
        this.username = username;
    }
    
    public boolean isUsernameExactMatches()
    {
        return usernameExactMatches;
    }
    
    public void setUsernameExactMatches(boolean usernameExactMatches)
    {
        this.usernameExactMatches = usernameExactMatches;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public Boolean getBuiltin()
    {
        return builtin;
    }
    
    public void setBuiltin(Boolean builtin)
    {
        this.builtin = builtin;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }

    public Set<String> getExcludeKeys() {
        return excludeKeys;
    }

    public void setExcludeKeys(Set<String> excludeKeys) {
        this.excludeKeys = excludeKeys;
    }
}
