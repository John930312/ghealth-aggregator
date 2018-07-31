package com.todaysoft.ghealth.agcy.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryOrdersRequest extends SignatureTokenListRequest
{
    private String id;
    
    private String agencyName;
    
    private String customerName;
    
    private String status;
    
    private String orderCode;
    
    private String productName;
    
    private String agencyId;
    
    private String startCreateTime;
    
    private String endStartTime;
    
    private String productId;
    
    private String createName;

    private String startReportTime;

    private String endReportTime;

    private String customerPhone;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("agencyName", agencyName);
        fields.put("customerName", customerName);
        fields.put("status", status);
        fields.put("orderCode", orderCode);
        fields.put("productName", productName);
        fields.put("productId", productId);
        fields.put("agencyId", agencyId);
        fields.put("endStartTime", endStartTime);
        fields.put("startCreateTime", startCreateTime);
        fields.put("endReportTime", endReportTime);
        fields.put("startReportTime", startReportTime);
        fields.put("createName", createName);
        fields.put("id", id);
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
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
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
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }

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

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
}