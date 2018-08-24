package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.math.BigDecimal;

/**
 * Created by xjw on 2017/9/13.
 */
public class MaintainOrderRequest extends SignatureTokenRequest
{
    
    private String id;
    
    private String code;
    
    private String customerId;
    
    private String customerName;
    
    private String productId;
    
    private String productName;
    
    private String productPhone;
    
    private String status;
    
    private String statusText;
    
    private BigDecimal price;
    
    private Integer reportPrintRequired;
    
    private String sampleType;
    
    private String agencyId;
    
    private BigDecimal actualPrice;
    
    private String fileName;
    
    private byte[] bytes;
    
    private String orderIds;
    
    private String objectKey;

    private String vigilance;
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public Integer getReportPrintRequired()
    {
        return reportPrintRequired;
    }
    
    public void setReportPrintRequired(Integer reportPrintRequired)
    {
        this.reportPrintRequired = reportPrintRequired;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
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
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getStatusText()
    {
        return statusText;
    }
    
    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }
    
    public BigDecimal getPrice()
    {
        return price;
    }
    
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
    
    public BigDecimal getActualPrice()
    {
        return actualPrice;
    }
    
    public void setActualPrice(BigDecimal actualPrice)
    {
        this.actualPrice = actualPrice;
    }
    
    public byte[] getBytes()
    {
        return bytes;
    }
    
    public void setBytes(byte[] bytes)
    {
        this.bytes = bytes;
    }
    
    public String getFileName()
    {
        return fileName;
    }
    
    public void setFileName(String fileName)
    {
        this.fileName = fileName;
    }
    
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
    
    public String getObjectKey()
    {
        return objectKey;
    }
    
    public void setObjectKey(String objectKey)
    {
        this.objectKey = objectKey;
    }

    public String getVigilance() {
        return vigilance;
    }

    public void setVigilance(String vigilance) {
        this.vigilance = vigilance;
    }
}
