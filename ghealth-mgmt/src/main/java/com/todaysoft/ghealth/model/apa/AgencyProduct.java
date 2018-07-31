package com.todaysoft.ghealth.model.apa;

import java.math.BigDecimal;
import java.util.Date;

public class AgencyProduct
{
    private String id;
    
    private String productId;
    
    private String productCode;
    
    private String productName;
    
    private String productSexRestraint;
    
    private String productSexRestraintText;
    
    private BigDecimal guidingPrice;
    
    private BigDecimal agencyPrice;
    
    private boolean productEnabled;
    
    private Date productCreateTime;
    
    private Date productUpdateTime;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getProductCode()
    {
        return productCode;
    }
    
    public void setProductCode(String productCode)
    {
        this.productCode = productCode;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getProductSexRestraint()
    {
        return productSexRestraint;
    }
    
    public void setProductSexRestraint(String productSexRestraint)
    {
        this.productSexRestraint = productSexRestraint;
    }
    
    public String getProductSexRestraintText()
    {
        return productSexRestraintText;
    }
    
    public void setProductSexRestraintText(String productSexRestraintText)
    {
        this.productSexRestraintText = productSexRestraintText;
    }
    
    public BigDecimal getGuidingPrice()
    {
        return guidingPrice;
    }
    
    public void setGuidingPrice(BigDecimal guidingPrice)
    {
        this.guidingPrice = guidingPrice;
    }
    
    public BigDecimal getAgencyPrice()
    {
        return agencyPrice;
    }
    
    public void setAgencyPrice(BigDecimal agencyPrice)
    {
        this.agencyPrice = agencyPrice;
    }
    
    public boolean isProductEnabled()
    {
        return productEnabled;
    }
    
    public void setProductEnabled(boolean productEnabled)
    {
        this.productEnabled = productEnabled;
    }
    
    public Date getProductCreateTime()
    {
        return productCreateTime;
    }
    
    public void setProductCreateTime(Date productCreateTime)
    {
        this.productCreateTime = productCreateTime;
    }
    
    public Date getProductUpdateTime()
    {
        return productUpdateTime;
    }
    
    public void setProductUpdateTime(Date productUpdateTime)
    {
        this.productUpdateTime = productUpdateTime;
    }
}
