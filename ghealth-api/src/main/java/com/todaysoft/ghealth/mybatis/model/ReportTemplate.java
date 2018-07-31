package com.todaysoft.ghealth.mybatis.model;

public class ReportTemplate extends PrimaryEntity
{
    private String code;
    
    private String name;
    
    private Product product;
    
    private boolean customized;
    
    private Agency agency;
    
    private String tsdgKey;
    
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
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public boolean isCustomized()
    {
        return customized;
    }
    
    public void setCustomized(boolean customized)
    {
        this.customized = customized;
    }
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
    }
    
    public String getTsdgKey()
    {
        return tsdgKey;
    }
    
    public void setTsdgKey(String tsdgKey)
    {
        this.tsdgKey = tsdgKey;
    }
}