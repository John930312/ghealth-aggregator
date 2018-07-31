package com.todaysoft.ghealth.model.reportTemplate;

import java.util.Date;

public class ReportTemplate
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String productId;
    
    private String productName;
    
    private boolean customized;
    
    private String agencyId;
    
    private String agencyAbbr;
    
    private String tsdgKey;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date updateTime;
    
    private String updatorName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public boolean isCustomized()
    {
        return customized;
    }
    
    public void setCustomized(boolean customized)
    {
        this.customized = customized;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyAbbr()
    {
        return agencyAbbr;
    }
    
    public void setAgencyAbbr(String agencyAbbr)
    {
        this.agencyAbbr = agencyAbbr;
    }
    
    public String getTsdgKey()
    {
        return tsdgKey;
    }
    
    public void setTsdgKey(String tsdgKey)
    {
        this.tsdgKey = tsdgKey;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
}
