package com.todaysoft.ghealth.base.response.model;

import com.todaysoft.ghealth.utils.ExcelField;

public class OrderUploadRequest
{
    private String id;
    
    private String customerName;
    
    private String sex;
    
    private String email;
    
    private String birthday;
    
    private String phone;
    
    private String productId;
    
    private String wantPaper;
    
    private String sampleType;
    
    @ExcelField(title = "ID号", align = 2, sort = 1)
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "客户姓名", align = 2, sort = 2)
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    @ExcelField(title = "性别", align = 2, sort = 3)
    public String getSex()
    {
        return sex;
    }
    
    public void setSex(String sex)
    {
        this.sex = sex;
    }
    
    @ExcelField(title = "邮箱", align = 2, sort = 4)
    public String getEmail()
    {
        return email;
    }
    
    public void setEmail(String email)
    {
        this.email = email;
    }
    
    @ExcelField(title = "出生年月", align = 2, sort = 5)
    public String getBirthday()
    {
        return birthday;
    }
    
    public void setBirthday(String birthday)
    {
        this.birthday = birthday;
    }
    
    @ExcelField(title = "客户联系方式", align = 2, sort = 6)
    public String getPhone()
    {
        return phone;
    }
    
    public void setPhone(String phone)
    {
        this.phone = phone;
    }
    
    @ExcelField(title = "检测项目", align = 2, sort = 7)
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    @ExcelField(title = "是否需要纸质报告", align = 2, sort = 8)
    public String getWantPaper()
    {
        return wantPaper;
    }
    
    public void setWantPaper(String wantPaper)
    {
        this.wantPaper = wantPaper;
    }
    
    @ExcelField(title = "标本类型", align = 2, sort = 9)
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
}
