package com.todaysoft.ghealth.mgmt.request;

import java.math.BigDecimal;
import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainAgencyRequest extends SignatureTokenRequest
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
    
    private String primaryUsername;
    
    private String primaryAccountPassword;
    
    private BigDecimal accountAmount;
    
    private BigDecimal authorizationAmount;

    private boolean primaryAccount;

    private boolean authorizationAmountFlag;

    private String rechargeType;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("code", code);
        fields.put("name", name);
        fields.put("abbr", abbr);
        fields.put("province", province);
        fields.put("city", city);
        fields.put("address", address);
        fields.put("contactName", contactName);
        fields.put("contactPhone", contactPhone);
        fields.put("contactEmail", contactEmail);
        fields.put("remark", remark);
        fields.put("primaryUsername", primaryUsername);
        fields.put("primaryAccountPassword", primaryAccountPassword);
        fields.put("accountAmount", String.valueOf(accountAmount));
        fields.put("authorizationAmount", String.valueOf(authorizationAmount));
        fields.put("primaryAccount", String.valueOf(primaryAccount));
        fields.put("authorizationAmountFlag", String.valueOf(authorizationAmountFlag));
        fields.put("rechargeType", rechargeType);
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
    
    public String getPrimaryUsername()
    {
        return primaryUsername;
    }
    
    public void setPrimaryUsername(String primaryUsername)
    {
        this.primaryUsername = primaryUsername;
    }
    
    public String getPrimaryAccountPassword()
    {
        return primaryAccountPassword;
    }
    
    public void setPrimaryAccountPassword(String primaryAccountPassword)
    {
        this.primaryAccountPassword = primaryAccountPassword;
    }
    
    public BigDecimal getAccountAmount()
    {
        return accountAmount;
    }
    
    public void setAccountAmount(BigDecimal accountAmount)
    {
        this.accountAmount = accountAmount;
    }
    
    public BigDecimal getAuthorizationAmount()
    {
        return authorizationAmount;
    }
    
    public void setAuthorizationAmount(BigDecimal authorizationAmount)
    {
        this.authorizationAmount = authorizationAmount;
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

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }
}
