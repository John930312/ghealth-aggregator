package com.todaysoft.ghealth.base.response.model;

import java.math.BigDecimal;
import java.util.List;

public class Agency
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String abbr;
    
    private String province;
    
    private String provinceText;
    
    private String city;
    
    private String cityText;
    
    private String address;
    
    private String contactName;
    
    private String contactPhone;
    
    private String contactEmail;
    
    private String remark;
    
    private BigDecimal accountAmount;
    
    private BigDecimal authorizationAmount;
    
    private String primaryUsername;
    
    private Long createTime;
    
    private String creatorName;
    
    private Long updateTime;
    
    private String updatorName;
    
    private boolean deleted;
    
    private Long deleteTime;
    
    private String deletorName;

    private boolean authorizationAmountFlag;

    private List<String> subAccount;

    public List<String> getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(List<String> subAccount) {
        this.subAccount = subAccount;
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
    
    public String getProvinceText()
    {
        return provinceText;
    }
    
    public void setProvinceText(String provinceText)
    {
        this.provinceText = provinceText;
    }
    
    public String getCity()
    {
        return city;
    }
    
    public void setCity(String city)
    {
        this.city = city;
    }
    
    public String getCityText()
    {
        return cityText;
    }
    
    public void setCityText(String cityText)
    {
        this.cityText = cityText;
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
    
    public BigDecimal getAuthorizationAmount()
    {
        return authorizationAmount;
    }
    
    public void setAuthorizationAmount(BigDecimal authorizationAmount)
    {
        this.authorizationAmount = authorizationAmount;
    }

    public boolean isAuthorizationAmountFlag() {
        return authorizationAmountFlag;
    }

    public void setAuthorizationAmountFlag(boolean authorizationAmountFlag) {
        this.authorizationAmountFlag = authorizationAmountFlag;
    }
}
