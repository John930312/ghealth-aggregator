package com.todaysoft.ghealth.model.customer;

import java.util.Date;

public class CustomerSearcher
{
    private String customerName;
    
    private String customerPhone;
    
    private String agencyName;
    
    private String agencyId;

    private Date startCreateTime;

    private Date endStartTime;
    
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
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }

    public Date getStartCreateTime() {
        return startCreateTime;
    }

    public void setStartCreateTime(Date startCreateTime) {
        this.startCreateTime = startCreateTime;
    }

    public Date getEndStartTime() {
        return endStartTime;
    }

    public void setEndStartTime(Date endStartTime) {
        this.endStartTime = endStartTime;
    }
}
