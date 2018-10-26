package com.todaysoft.ghealth.open.api.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class AgencyProduct extends PrimaryEntity
{
    private BigDecimal agencyPrice;
    
    private String discountPrice;
    
    private boolean discount;
    
    public BigDecimal getAgencyPrice()
    {
        return agencyPrice;
    }
    
    public void setAgencyPrice(BigDecimal agencyPrice)
    {
        this.agencyPrice = agencyPrice;
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
}
