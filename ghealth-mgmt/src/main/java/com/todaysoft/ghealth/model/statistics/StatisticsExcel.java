package com.todaysoft.ghealth.model.statistics;

import com.todaysoft.ghealth.model.Order;

import java.math.BigDecimal;

public class StatisticsExcel {


    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }


    public String getReportRequired() {
        return reportRequired;
    }

    public void setReportRequired(String reportRequired) {
        this.reportRequired = reportRequired;
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String orderId;

    private String customerName;

    private String productName;

    private String reportRequired;

    private String agencyName;

    private String actualPrice;

    private String eventTime;

    private String status;

    public String getActualPrice() {
        return actualPrice;
    }

    public void setActualPrice(String actualPrice) {
        this.actualPrice = actualPrice;
    }
}
