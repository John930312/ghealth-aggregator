package com.todaysoft.ghealth.base.response.model;

import java.math.BigDecimal;

public class AgencyProduct
{
    private String id;
    
    private String agencyId;
    
    private String agencyCode;
    
    private String agencyAbbr;
    
    private String productId;
    
    private String productCode;
    
    private String productName;
    
    private String productSexRestraint;
    
    private String productSexRestraintText;
    
    private BigDecimal guidingPrice;
    
    private BigDecimal agencyPrice;
    
    private boolean productEnabled;
    
    private Long productCreateTime;
    
    private String productCreatorName;
    
    private Long productUpdateTime;
    
    private String productUpdatorName;
    
    private BigDecimal agencyAccountAmount;
    
    private String agencyName;
    
    private BigDecimal agencyAuthorizationAmount;
    
    private boolean discount;
    
    private String discountPrice;
    
    private Long startTime;
    
    private Long endTime;
    
    private boolean inGracePeriod;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
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
    
    public Long getProductCreateTime()
    {
        return productCreateTime;
    }
    
    public void setProductCreateTime(Long productCreateTime)
    {
        this.productCreateTime = productCreateTime;
    }
    
    public String getProductCreatorName()
    {
        return productCreatorName;
    }
    
    public void setProductCreatorName(String productCreatorName)
    {
        this.productCreatorName = productCreatorName;
    }
    
    public Long getProductUpdateTime()
    {
        return productUpdateTime;
    }
    
    public void setProductUpdateTime(Long productUpdateTime)
    {
        this.productUpdateTime = productUpdateTime;
    }
    
    public String getProductUpdatorName()
    {
        return productUpdatorName;
    }
    
    public void setProductUpdatorName(String productUpdatorName)
    {
        this.productUpdatorName = productUpdatorName;
    }
    
    public BigDecimal getAgencyAccountAmount()
    {
        return agencyAccountAmount;
    }
    
    public void setAgencyAccountAmount(BigDecimal agencyAccountAmount)
    {
        this.agencyAccountAmount = agencyAccountAmount;
    }
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    public BigDecimal getAgencyAuthorizationAmount()
    {
        return agencyAuthorizationAmount;
    }
    
    public void setAgencyAuthorizationAmount(BigDecimal agencyAuthorizationAmount)
    {
        this.agencyAuthorizationAmount = agencyAuthorizationAmount;
    }
    
    public Long getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Long endTime)
    {
        this.endTime = endTime;
    }
    
    public Long getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Long startTime)
    {
        this.startTime = startTime;
    }
    
    public String getDiscountPrice()
    {
        return discountPrice;
    }
    
    public void setDiscountPrice(String discountPrice)
    {
        this.discountPrice = discountPrice;
    }
    
    public boolean isDiscount()
    {
        return discount;
    }
    
    public void setDiscount(boolean discount)
    {
        this.discount = discount;
    }
    
    public boolean isInGracePeriod()
    {
        return inGracePeriod;
    }
    
    public void setInGracePeriod(boolean inGracePeriod)
    {
        this.inGracePeriod = inGracePeriod;
    }
}
