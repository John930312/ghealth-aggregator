package com.todaysoft.ghealth.service.model.reportData;

import java.util.List;

public class OrderData
{
    private String checkupName;
    
    private String examineBarcode;
    
    private String name;
    
    private String sex;
    
    private String sampleType;
    
    private String sampleReceiveDate;
    
    private String checkupGenDate;
    
    private List<ItemData> itemList;
    
    public String getCheckupName()
    {
        return checkupName;
    }
    
    public void setCheckupName(String checkupName)
    {
        this.checkupName = checkupName;
    }
    
    public String getExamineBarcode()
    {
        return examineBarcode;
    }
    
    public void setExamineBarcode(String examineBarcode)
    {
        this.examineBarcode = examineBarcode;
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
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getSampleReceiveDate()
    {
        return sampleReceiveDate;
    }
    
    public void setSampleReceiveDate(String sampleReceiveDate)
    {
        this.sampleReceiveDate = sampleReceiveDate;
    }
    
    public String getCheckupGenDate()
    {
        return checkupGenDate;
    }
    
    public void setCheckupGenDate(String checkupGenDate)
    {
        this.checkupGenDate = checkupGenDate;
    }
    
    public List<ItemData> getItemList()
    {
        return itemList;
    }
    
    public void setItemList(List<ItemData> itemList)
    {
        this.itemList = itemList;
    }
}
