package com.todaysoft.ghealth.wechat.model;

import java.util.Date;

public class TestingItem
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String category;
    
    private String categoryText;
    
    private String categoryMapping;
    
    private String sexRestraint;
    
    private String sexRestraintText;
    
    private boolean enabled;
    
    private String enabledString;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date updateTime;
    
    private String updatorName;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String deletorName;
    
    @Override
    public int hashCode()
    {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        return result;
    }
    
    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        TestingItem other = (TestingItem)obj;
        if (id == null)
        {
            if (other.id != null)
                return false;
        }
        else if (!id.equals(other.id))
            return false;
        return true;
    }
    
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
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getCategoryText()
    {
        return categoryText;
    }
    
    public void setCategoryText(String categoryText)
    {
        this.categoryText = categoryText;
    }
    
    public String getCategoryMapping()
    {
        return categoryMapping;
    }
    
    public void setCategoryMapping(String categoryMapping)
    {
        this.categoryMapping = categoryMapping;
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
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public String getEnabledString()
    {
        return enabledString;
    }
    
    public void setEnabledString(String enabledString)
    {
        this.enabledString = enabledString;
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
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
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
