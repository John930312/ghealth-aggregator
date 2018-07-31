package com.todaysoft.ghealth.mybatis.model;

import java.util.Date;

public class SmsSend
{
    private String id;
    
    private String agencyId;
    
    private String status;
    
    private String phone;
    
    private Date createTime;
    
    private String templateId;
    
    private Boolean sended;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getTemplateId()
    {
        return templateId;
    }
    
    public void setTemplateId(String templateId)
    {
        this.templateId = templateId;
    }
    
    public Boolean getSended()
    {
        return sended;
    }
    
    public void setSended(Boolean sended)
    {
        this.sended = sended;
    }
}