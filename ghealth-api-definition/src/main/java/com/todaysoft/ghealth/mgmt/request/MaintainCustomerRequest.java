package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainCustomerRequest extends SignatureTokenRequest
{
    private String id;
    
    private String name;
    
    private String sex;
    
    private String phone;
    
    private String email;
    
    private String vocation;
    
    private String company;
    
    private String maritalStatus;
    
    private String nation;
    
    private String birthday;
    
    private String province;
    
    private String city;
    
    private String county;
    
    private String address;
    
    private String remark;
    
    private String district;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("name", name);
        fields.put("sex", sex);
        fields.put("phone", phone);
        fields.put("email", email);
        fields.put("birthday", birthday);
        fields.put("province", province);
        fields.put("city", city);
        fields.put("county", county);
        fields.put("address", address);
        fields.put("remark", remark);
        fields.put("vocation", vocation);
        fields.put("company", company);
        fields.put("maritalStatus", maritalStatus);
        fields.put("nation", nation);
        fields.put("district", district);
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
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
    
    public String getCounty()
    {
        return county;
    }
    
    public void setCounty(String county)
    {
        this.county = county;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public void setAddress(String address)
    {
        this.address = address;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getVocation()
    {
        return vocation;
    }
    
    public void setVocation(String vocation)
    {
        this.vocation = vocation;
    }
    
    public String getCompany()
    {
        return company;
    }
    
    public void setCompany(String company)
    {
        this.company = company;
    }
    
    public String getMaritalStatus()
    {
        return maritalStatus;
    }
    
    public void setMaritalStatus(String maritalStatus)
    {
        this.maritalStatus = maritalStatus;
    }
    
    public String getNation()
    {
        return nation;
    }
    
    public void setNation(String nation)
    {
        this.nation = nation;
    }
    
    public String getDistrict()
    {
        return district;
    }
    
    public void setDistrict(String district)
    {
        this.district = district;
    }
}
