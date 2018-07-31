package com.todaysoft.ghealth.model.agency;

import java.math.BigDecimal;
import java.util.Date;

public class Agency
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String abbr;
    
    private String province;
    
    private String city;
    
    private String address;
    
    private String contactName;
    
    private String contactPhone;
    
    private String contactEmail;
    
    private String remark;
    
    private BigDecimal accountAmount;
    
    private String primaryUsername;

    private String primaryAccountPassword;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date updateTime;
    
    private String updatorName;
    
    private boolean deleted;
    
    private Date deleteTime;
    
    private String deletorName;

    private String primaryAccount;

    private boolean authorizationAmountFlag;
    
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
    
    public String getAbbr()
    {
        return abbr;
    }
    
    public void setAbbr(String abbr)
    {
        this.abbr = abbr;
    }
    
    public String getProvince()
    {
        return province;
    }
    
    public void setProvince(String province)
    {
        this.province = province;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getContactName()
    {
        return contactName;
    }
    
    public void setContactName(String contactName)
    {
        this.contactName = contactName;
    }
    
    public String getContactPhone()
    {
        return contactPhone;
    }
    
    public void setContactPhone(String contactPhone)
    {
        this.contactPhone = contactPhone;
    }
    
    public String getContactEmail()
    {
        return contactEmail;
    }
    
    public void setContactEmail(String contactEmail)
    {
        this.contactEmail = contactEmail;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public BigDecimal getAccountAmount()
    {
        return accountAmount;
    }
    
    public void setAccountAmount(BigDecimal accountAmount)
    {
        this.accountAmount = accountAmount;
    }
    
    public String getPrimaryUsername()
    {
        return primaryUsername;
    }
    
    public void setPrimaryUsername(String primaryUsername)
    {
        this.primaryUsername = primaryUsername;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
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
    
    public String getDeletorName()
    {
        return deletorName;
    }
    
    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
    {
        this.deleteTime = deleteTime;
    }

    public String getPrimaryAccountPassword() {
        return primaryAccountPassword;
    }

    public void setPrimaryAccountPassword(String primaryAccountPassword) {
        this.primaryAccountPassword = primaryAccountPassword;
    }

    public String getPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(String primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public boolean isAuthorizationAmountFlag() {
        return authorizationAmountFlag;
    }

    public void setAuthorizationAmountFlag(boolean authorizationAmountFlag) {
        this.authorizationAmountFlag = authorizationAmountFlag;
    }
}
