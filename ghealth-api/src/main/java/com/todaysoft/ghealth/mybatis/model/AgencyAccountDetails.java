package com.todaysoft.ghealth.mybatis.model;

public class AgencyAccountDetails
{
    private String id;
    
    private String accountName;
    
    private String agencyId;
    
    private String agencyCode;
    
    private String agencyAbbr;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getAccountName()
    {
        return accountName;
    }
    
    public void setAccountName(String accountName)
    {
        this.accountName = accountName;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyCode()
    {
        return agencyCode;
    }
    
    public void setAgencyCode(String agencyCode)
    {
        this.agencyCode = agencyCode;
    }
    
    public String getAgencyAbbr()
    {
        return agencyAbbr;
    }
    
    public void setAgencyAbbr(String agencyAbbr)
    {
        this.agencyAbbr = agencyAbbr;
    }
}
