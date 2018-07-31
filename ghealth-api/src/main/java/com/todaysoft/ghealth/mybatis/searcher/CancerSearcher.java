package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Set;

public class CancerSearcher
{
    private String cancerName;

    private boolean nameExactMatches;

    private Set<String> excludeKeys;
    
    private Integer offset;
    
    private Integer limit;
    
    public String getCancerName()
    {
        return cancerName;
    }
    
    public void setCancerName(String cancerName)
    {
        this.cancerName = cancerName;
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

    public boolean isNameExactMatches() {
        return nameExactMatches;
    }

    public void setNameExactMatches(boolean nameExactMatches) {
        this.nameExactMatches = nameExactMatches;
    }

    public Set<String> getExcludeKeys() {
        return excludeKeys;
    }

    public void setExcludeKeys(Set<String> excludeKeys) {
        this.excludeKeys = excludeKeys;
    }
}
