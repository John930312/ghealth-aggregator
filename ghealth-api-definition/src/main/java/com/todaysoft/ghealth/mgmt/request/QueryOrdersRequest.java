package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryOrdersRequest extends SignatureTokenListRequest
{
    private String agencyName;
    
    private String customerName;
    
    private String status;
    
    private String orderCode;
    
    private String productId;
    
    private String startCreateTime;

    private String endStartTime;
    
    private String productName;
    
    private String agencyId;
    
    private String createName;

    private String startReportTime;

    private String endReportTime;

    private String primaryUsername;

    private String customerPhone;
    
    private String vigilance;

    public String getStartReportTime() {
        return startReportTime;
    }

    public void setStartReportTime(String startReportTime) {
        this.startReportTime = startReportTime;
    }

    public String getEndReportTime() {
        return endReportTime;
    }

    public void setEndReportTime(String endReportTime) {
        this.endReportTime = endReportTime;
    }

    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("agencyName", agencyName);
        fields.put("agencyId", agencyId);
        fields.put("customerName", customerName);
        fields.put("status", status);
        fields.put("orderCode", orderCode);
        fields.put("productId", productId);
        fields.put("productName", productName);
        fields.put("endStartTime", endStartTime);
        fields.put("startCreateTime", startCreateTime);
        fields.put("createName", createName);
        fields.put("primaryUsername", primaryUsername);
        fields.put("customerPhone", customerPhone);
    }


    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
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
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getStartCreateTime()
    {
        return startCreateTime;
    }
    
    public void setStartCreateTime(String startCreateTime)
    {
        this.startCreateTime = startCreateTime;
    }
    
    public String getEndStartTime()
    {
        return endStartTime;
    }
    
    public void setEndStartTime(String endStartTime)
    {
        this.endStartTime = endStartTime;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
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
    
    public String getVigilance()
    {
        return vigilance;
    }
    
    public void setVigilance(String vigilance)
    {
        this.vigilance = vigilance;
    }
}