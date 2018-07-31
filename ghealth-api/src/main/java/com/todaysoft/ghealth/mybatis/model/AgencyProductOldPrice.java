package com.todaysoft.ghealth.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

public class AgencyProductOldPrice
{
    private String id;

    private String agencyProductId;

    private BigDecimal oldAgencyPrice;

    private BigDecimal changedAgencyPrice;

    private String detail;

    private Date operateTime;

    private String operator;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAgencyProductId() {
        return agencyProductId;
    }

    public void setAgencyProductId(String agencyProductId) {
        this.agencyProductId = agencyProductId;
    }

    public BigDecimal getOldAgencyPrice() {
        return oldAgencyPrice;
    }

    public void setOldAgencyPrice(BigDecimal oldAgencyPrice) {
        this.oldAgencyPrice = oldAgencyPrice;
    }

    public BigDecimal getChangedAgencyPrice() {
        return changedAgencyPrice;
    }

    public void setChangedAgencyPrice(BigDecimal changedAgencyPrice) {
        this.changedAgencyPrice = changedAgencyPrice;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public String getOperator() {
        return operator;
    }

    public void setOperator(String operator) {
        this.operator = operator;
    }
}
