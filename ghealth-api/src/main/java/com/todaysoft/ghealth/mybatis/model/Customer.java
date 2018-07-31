package com.todaysoft.ghealth.mybatis.model;

public class Customer extends PrimaryEntity
{
    public static final String SEX_MALE = "1";
    
    public static final String SEX_FEMALE = "2";
    
    private String agencyId;
    
    private String agencyCode;
    
    private String agencyAbbr;
    
    private String name;
    
    private String phone;
    
    private String email;
    
    private String sex;
    
    private String birthday;
    
    private String province;
    
    private String city;
    
    private String county;
    
    private String address;
    
    private String remark;
    
    private String vocation;
    
    private String company;
    
    private String maritalStatus;
    
    private String nation;
    
    private String district;
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyCode()
    {
        return agencyCode;
    }
    
    public void setAgencyCode(String agencyCode)
    {
        this.agencyCode = agencyCode;
    }
    
    public String getAgencyAbbr()
    {
        return agencyAbbr;
    }
    
    public void setAgencyAbbr(String agencyAbbr)
    {
        this.agencyAbbr = agencyAbbr;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
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
    
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
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
    
    public Customer()
    {
    }
    
    public String getDistrict()
    {
        return district;
    }
    
    public void setDistrict(String district)
    {
        this.district = district;
    }
    
    public Customer(String name, String phone, String email, String sex, String birthday)
    {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.sex = sex;
        this.birthday = birthday;
    }
}