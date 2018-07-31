package com.todaysoft.ghealth.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class AgencyProduct extends PrimaryEntity
{

    private Agency agency;
    
    private Product product;
    
    private BigDecimal agencyPrice;

    private Date startTime;

    private Date endTime;

    private String discountPrice;

    private boolean discount;
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    public BigDecimal getAgencyPrice()
    {
        return agencyPrice;
    }
    
    public void setAgencyPrice(BigDecimal agencyPrice)
    {
        this.agencyPrice = agencyPrice;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public String getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(String discountPrice) {
        this.discountPrice = discountPrice;
    }

    public boolean isDiscount() {
        return discount;
    }

    public void setDiscount(boolean discount) {
        this.discount = discount;
    }
}
