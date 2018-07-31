package com.todaysoft.ghealth.mgmt.request;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

/**
 * Created by xjw on 2017/9/14.
 */
public class MaintainProductAgencyRequest extends SignatureTokenRequest
{
    
    private String agencyId;
    
    private String testingProductId;
    
    private BigDecimal agencyPrice;

    private String agencyIds;

    private Long startTime;

    private Long endTime;

    private String discountPrice;

    private boolean discount;

    private String testingProductIds;

    private String detail;//代理价格调整备注

    private String changeType;
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getTestingProductId()
    {
        return testingProductId;
    }
    
    public void setTestingProductId(String testingProductId)
    {
        this.testingProductId = testingProductId;
    }
    
    public BigDecimal getAgencyPrice()
    {
        return agencyPrice;
    }
    
    public void setAgencyPrice(BigDecimal agencyPrice)
    {
        this.agencyPrice = agencyPrice;
    }

    public String getAgencyIds() {
        return agencyIds;
    }

    public void setAgencyIds(String agencyIds) {
        this.agencyIds = agencyIds;
    }

    public Long getStartTime() {
        return startTime;
    }

    public void setStartTime(Long startTime) {
        this.startTime = startTime;
    }

    public Long getEndTime() {
        return endTime;
    }

    public void setEndTime(Long endTime) {
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

    public String getTestingProductIds() {
        return testingProductIds;
    }

    public void setTestingProductIds(String testingProductIds) {
        this.testingProductIds = testingProductIds;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getChangeType() {
        return changeType;
    }

    public void setChangeType(String changeType) {
        this.changeType = changeType;
    }
}
