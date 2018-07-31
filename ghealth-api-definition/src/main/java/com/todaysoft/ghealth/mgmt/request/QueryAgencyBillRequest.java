package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

import java.util.Date;
import java.util.Map;

public class QueryAgencyBillRequest extends SignatureTokenListRequest
{
    private String agencyName;
    
    private String productName;
    
    private String startTime;
    
    private String endTime;

    private String orderCode;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("agencyName", agencyName);
        fields.put("productName", productName);
        fields.put("orderCode", orderCode);
        fields.put("startTime", String.valueOf(startTime));
        fields.put("startTime", String.valueOf(endTime));
    }
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }
}
