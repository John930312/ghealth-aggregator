package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Set;

public class CustomerSearcher
{
    private String agencyId;
    
    private String agencyName;
    
    private String customerName;
    
    private String customerPhone;
    
    private boolean phoneExactMatches;
    
    private Set<String> excludeKeys;
    
    private String startCreateTime;
    
    private String endStartTime;
    
    private String sex;
    
    private Integer offset;
    
    private Integer limit;
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getCustomerPhone()
    {
        return customerPhone;
    }
    
    public void setCustomerPhone(String customerPhone)
    {
        this.customerPhone = customerPhone;
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
    
    public CustomerSearcher()
    {
    }
    
    public CustomerSearcher(String agencyId, String customerName, String customerPhone)
    {
        this.agencyId = agencyId;
        this.customerName = customerName;
        this.customerPhone = customerPhone;
    }
    
    public boolean isPhoneExactMatches()
    {
        return phoneExactMatches;
    }
    
    public void setPhoneExactMatches(boolean phoneExactMatches)
    {
        this.phoneExactMatches = phoneExactMatches;
    }
    
    public Set<String> getExcludeKeys()
    {
        return excludeKeys;
    }
    
    public void setExcludeKeys(Set<String> excludeKeys)
    {
        this.excludeKeys = excludeKeys;
    }
    
    public String getStartCreateTime()
    {
        return startCreateTime;
    }
    
    public void setStartCreateTime(String startCreateTime)
    {
        this.startCreateTime = startCreateTime;
    }
    
    public String getEndStartTime()
    {
        return endStartTime;
    }
    
    public void setEndStartTime(String endStartTime)
    {
        this.endStartTime = endStartTime;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
}
