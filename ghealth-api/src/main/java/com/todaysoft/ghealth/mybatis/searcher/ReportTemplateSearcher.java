package com.todaysoft.ghealth.mybatis.searcher;

public class ReportTemplateSearcher
{
    private String excludeId;
    
    private String name;
    
    private String productId;
    
    private String productName;
    
    private Boolean customized;
    
    private String agencyId;
    
    private String agencyName;
    
    private String code;
    
    private Integer offset;
    
    private Integer limit;
    
    public String getExcludeId()
    {
        return excludeId;
    }
    
    public void setExcludeId(String excludeId)
    {
        this.excludeId = excludeId;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public Boolean getCustomized()
    {
        return customized;
    }
    
    public void setCustomized(Boolean customized)
    {
        this.customized = customized;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
}
