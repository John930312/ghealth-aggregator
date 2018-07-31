package com.todaysoft.ghealth.agcy.request;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

import java.util.Map;

public class QueryAgencyBillRequest extends SignatureTokenListRequest
{
    private String productName;

    private String orderCode;
    
    private String startTime;
    
    private String endTime;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("productName", productName);
        fields.put("orderCode", orderCode);
        fields.put("startTime", String.valueOf(startTime));
        fields.put("startTime", String.valueOf(endTime));
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
