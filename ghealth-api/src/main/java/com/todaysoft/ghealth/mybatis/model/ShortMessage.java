package com.todaysoft.ghealth.mybatis.model;

import java.util.Date;

public class ShortMessage extends PrimaryEntity
{
    private String configDetails;
    
    private Boolean agencyCustomized;
    
    private String agencyId;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date updateTime;
    
    private String updatorName;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String deletorName;
    
    public String getConfigDetails()
    {
        return configDetails;
    }
    
    public void setConfigDetails(String configDetails)
    {
        this.configDetails = configDetails;
    }
    
    public Boolean getAgencyCustomized()
    {
        return agencyCustomized;
    }
    
    public void setAgencyCustomized(Boolean agencyCustomized)
    {
        this.agencyCustomized = agencyCustomized;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    @Override
    public Date getCreateTime()
    {
        return createTime;
    }
    
    @Override
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @Override
    public String getCreatorName()
    {
        return creatorName;
    }
    
    @Override
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    @Override
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    @Override
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    @Override
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    @Override
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
    
    @Override
    public boolean isDeleted()
    {
        return deleted;
    }
    
    @Override
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    @Override
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    @Override
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    @Override
    public String getDeletorName()
    {
        return deletorName;
    }
    
    @Override
    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }
    
}
