package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.util.Date;

/**
 * @Author: ljl
 * @Date: 2018/9/12 0012 9:45
 */
public class MaintainSmsSendRequest extends SignatureTokenRequest
{
    private String agencyId;
    
    private String status;
    
    private String createTime;
    
    private String templateId;
    
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
    
    public String getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(String createTime)
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
}
