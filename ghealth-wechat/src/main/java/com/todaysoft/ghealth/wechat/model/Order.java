package com.todaysoft.ghealth.wechat.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order
{
    private String id;
    
    private String code;
    
    private String status;
    
    private String statusText;
    
    private BigDecimal actualPrice;
    
    private Date createTime;
    
    private Date submitTime;
    
    private String reasonType;
    
    private Integer reportPrintRequired;
    
    private String sampleType;
    
    private ProductDetails product;
    
    private Customer customer;
    
    private List<OrderHistory> orderHistoryList;
    
    private String sampleTypeText;
    
    private String reportPrintRequiredText;
    
    private Agency agency;
    
    private byte[] reportContents;
    
    private boolean existContents = false;
    
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
    
    public BigDecimal getActualPrice()
    {
        return actualPrice;
    }
    
    public void setActualPrice(BigDecimal actualPrice)
    {
        this.actualPrice = actualPrice;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getReasonType()
    {
        return reasonType;
    }
    
    public void setReasonType(String reasonType)
    {
        this.reasonType = reasonType;
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
    
    public ProductDetails getProduct()
    {
        return product;
    }
    
    public void setProduct(ProductDetails product)
    {
        this.product = product;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public List<OrderHistory> getOrderHistoryList()
    {
        return orderHistoryList;
    }
    
    public void setOrderHistoryList(List<OrderHistory> orderHistoryList)
    {
        this.orderHistoryList = orderHistoryList;
    }
    
    public String getSampleTypeText()
    {
        return sampleTypeText;
    }
    
    public void setSampleTypeText(String sampleTypeText)
    {
        this.sampleTypeText = sampleTypeText;
    }
    
    public String getReportPrintRequiredText()
    {
        return reportPrintRequiredText;
    }
    
    public void setReportPrintRequiredText(String reportPrintRequiredText)
    {
        this.reportPrintRequiredText = reportPrintRequiredText;
    }
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
    }
    
    public byte[] getReportContents()
    {
        return reportContents;
    }
    
    public void setReportContents(byte[] reportContents)
    {
        this.reportContents = reportContents;
    }
    
    public boolean isExistContents()
    {
        return existContents;
    }
    
    public void setExistContents(boolean existContents)
    {
        this.existContents = existContents;
    }
}
