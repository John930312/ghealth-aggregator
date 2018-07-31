package com.todaysoft.ghealth.mybatis.model;

import java.math.BigDecimal;
import java.util.List;

public class Agency extends PrimaryEntity
{
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
    
    private String amountSignature;
    
    private String primaryUsername;

    private String primaryAccountPassword;
    
    private String fullName;
    
    private BigDecimal authorizationAmount;

    private boolean primaryAccount;

    private boolean authorizationAmountFlag;
    private List<String> subAccount;


    public List<String> getSubAccount() {
        return subAccount;
    }

    public void setSubAccount(List<String> subAccount) {
        this.subAccount = subAccount;
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
    
    public String getAmountSignature()
    {
        return amountSignature;
    }
    
    public void setAmountSignature(String amountSignature)
    {
        this.amountSignature = amountSignature;
    }
    
    public String getPrimaryUsername()
    {
        return primaryUsername;
    }
    
    public void setPrimaryUsername(String primaryUsername)
    {
        this.primaryUsername = primaryUsername;
    }
    
    public String getFullName()
    {
        return fullName;
    }
    
    public void setFullName(String fullName)
    {
        this.fullName = fullName;
    }
    
    public BigDecimal getAuthorizationAmount()
    {
        return authorizationAmount;
    }
    
    public void setAuthorizationAmount(BigDecimal authorizationAmount)
    {
        this.authorizationAmount = authorizationAmount;
    }

    public String getPrimaryAccountPassword() {
        return primaryAccountPassword;
    }

    public void setPrimaryAccountPassword(String primaryAccountPassword) {
        this.primaryAccountPassword = primaryAccountPassword;
    }

    public boolean isPrimaryAccount() {
        return primaryAccount;
    }

    public void setPrimaryAccount(boolean primaryAccount) {
        this.primaryAccount = primaryAccount;
    }

    public boolean isAuthorizationAmountFlag() {
        return authorizationAmountFlag;
    }

    public void setAuthorizationAmountFlag(boolean authorizationAmountFlag) {
        this.authorizationAmountFlag = authorizationAmountFlag;
    }
}
