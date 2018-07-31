package com.todaysoft.ghealth.base.response.model;

import java.math.BigDecimal;

public class Product
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String sexRestraint;
    
    private String sexRestraintText;
    
    private BigDecimal guidingPrice;
    
    private boolean enabled;
    
    private Long createTime;
    
    private String creatorName;
    
    private Long updateTime;
    
    private String updatorName;
    
    private boolean deleted;
    
    private Long deleteTime;
    
    private String deletorName;
    
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
    
    public String getSexRestraint()
    {
        return sexRestraint;
    }
    
    public void setSexRestraint(String sexRestraint)
    {
        this.sexRestraint = sexRestraint;
    }
    
    public String getSexRestraintText()
    {
        return sexRestraintText;
    }
    
    public void setSexRestraintText(String sexRestraintText)
    {
        this.sexRestraintText = sexRestraintText;
    }
    
    public BigDecimal getGuidingPrice()
    {
        return guidingPrice;
    }
    
    public void setGuidingPrice(BigDecimal guidingPrice)
    {
        this.guidingPrice = guidingPrice;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public Long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Long createTime)
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
    
    public Long getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Long updateTime)
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
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Long getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Long deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getDeletorName()
    {
        return deletorName;
    }
    
    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }
}
