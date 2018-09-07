package com.todaysoft.ghealth.open.api.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class Order extends PrimaryEntity
{
    private String code;
    
    private Agency agency;
    
    private Product product;
    
    private Customer customer;
    
    private BigDecimal amount;
    
    private String sampleType;
    
    private boolean printReport;
    
    private Date submitTime;
    
    private String submitorName;
    
    private String status;
    
    private Date reportGenerateTime;
    
    private String pdfUrl;
    
    private List<OrderHistory> orderHistoryList;
    
    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
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
    
    public BigDecimal getAmount()
    {
        return amount;
    }
    
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
    
    public boolean isPrintReport()
    {
        return printReport;
    }
    
    public void setPrintReport(boolean printReport)
    {
        this.printReport = printReport;
    }
    
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public String getSubmitorName()
    {
        return submitorName;
    }
    
    public void setSubmitorName(String submitorName)
    {
        this.submitorName = submitorName;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Date getReportGenerateTime()
    {
        return reportGenerateTime;
    }
    
    public void setReportGenerateTime(Date reportGenerateTime)
    {
        this.reportGenerateTime = reportGenerateTime;
    }
    
    public String getPdfUrl()
    {
        return pdfUrl;
    }
    
    public void setPdfUrl(String pdfUrl)
    {
        this.pdfUrl = pdfUrl;
    }
    
    public List<OrderHistory> getOrderHistoryList()
    {
        return orderHistoryList;
    }
    
    public void setOrderHistoryList(List<OrderHistory> orderHistoryList)
    {
        this.orderHistoryList = orderHistoryList;
    }

}
