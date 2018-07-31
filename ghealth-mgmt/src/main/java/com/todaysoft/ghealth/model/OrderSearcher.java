package com.todaysoft.ghealth.model;

import java.util.Date;

public class OrderSearcher
{
    private String orderCode;
    
    private String agencyId;
    
    private String agencyName;
    
    private String productId;
    
    private String productName;
    
    private String customerName;
    
    private String status;
    
    private Date startTime;
    
    private Date endTime;

    private Date startReportTime;

    private Date endReportTime;
    
    private String createName;

    private String primaryUsername;

    private String customerPhone;
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
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
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Date getStartTime()
    {
        return startTime;
    }
    
    public void setStartTime(Date startTime)
    {
        this.startTime = startTime;
    }
    
    public Date getEndTime()
    {
        return endTime;
    }
    
    public void setEndTime(Date endTime)
    {
        this.endTime = endTime;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public Date getStartReportTime() {
        return startReportTime;
    }

    public void setStartReportTime(Date startReportTime) {
        this.startReportTime = startReportTime;
    }

    public Date getEndReportTime() {
        return endReportTime;
    }

    public void setEndReportTime(Date endReportTime) {
        this.endReportTime = endReportTime;
    }

    public String getPrimaryUsername() {
        return primaryUsername;
    }

    public void setPrimaryUsername(String primaryUsername) {
        this.primaryUsername = primaryUsername;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}
