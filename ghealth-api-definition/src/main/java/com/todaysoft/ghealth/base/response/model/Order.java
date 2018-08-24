package com.todaysoft.ghealth.base.response.model;

import java.math.BigDecimal;
import java.util.List;

public class Order
{
    private String id;
    
    private ProductDetails product;
    
    private Agency agency;
    
    private Customer customer;
    
    private String code;
    
    private BigDecimal actualPrice;
    
    private String status;
    
    private Long submitTime;
    
    private String submitorName;
    
    private Long createTime;
    
    private Long reportGenerateTime;
    
    private Long operateTime;
    
    private String creatorName;
    
    private Long updateTime;
    
    private String updatorName;
    
    private boolean deleted;
    
    private Long deleteTime;
    
    private String deletorName;
    
    private Integer reportPrintRequired;
    
    private String sampleType;
    
    private String reportGenerateTaskId;
    
    private String dataDetails;
    
    private Boolean havePDF;
    
    private Boolean haveWord;
    
    private Integer reportDownloadCount;
    
    private String reportPrintRequiredText;
    
    private String statusText;
    
    private String sampleTypeText;
    
    private List<OrderHistory> OrderHistoryList;
    
    private byte[] reportContents;

    private String vigilance;
    
    public Long getReportGenerateTime()
    {
        return reportGenerateTime;
    }
    
    public void setReportGenerateTime(Long reportGenerateTime)
    {
        this.reportGenerateTime = reportGenerateTime;
    }
    
    public Long getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Long operateTime)
    {
        this.operateTime = operateTime;
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
    
    public BigDecimal getActualPrice()
    {
        return actualPrice;
    }
    
    public void setActualPrice(BigDecimal actualPrice)
    {
        this.actualPrice = actualPrice;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Long getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Long submitTime)
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
    
    public Long getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Long createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Long getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Long updateTime)
    {
        this.updateTime = updateTime;
    }
    
    public String getUpdatorName()
    {
        return updatorName;
    }
    
    public void setUpdatorName(String updatorName)
    {
        this.updatorName = updatorName;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Long getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Long deleteTime)
    {
        this.deleteTime = deleteTime;
    }
    
    public String getDeletorName()
    {
        return deletorName;
    }
    
    public void setDeletorName(String deletorName)
    {
        this.deletorName = deletorName;
    }
    
    public ProductDetails getProduct()
    {
        return product;
    }
    
    public void setProduct(ProductDetails product)
    {
        this.product = product;
    }
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
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
    
    public String getReportGenerateTaskId()
    {
        return reportGenerateTaskId;
    }
    
    public void setReportGenerateTaskId(String reportGenerateTaskId)
    {
        this.reportGenerateTaskId = reportGenerateTaskId;
    }
    
    public String getDataDetails()
    {
        return dataDetails;
    }
    
    public void setDataDetails(String dataDetails)
    {
        this.dataDetails = dataDetails;
    }
    
    public Boolean getHavePDF()
    {
        return havePDF;
    }
    
    public void setHavePDF(Boolean havePDF)
    {
        this.havePDF = havePDF;
    }
    
    public Boolean getHaveWord()
    {
        return haveWord;
    }
    
    public void setHaveWord(Boolean haveWord)
    {
        this.haveWord = haveWord;
    }
    
    public Integer getReportDownloadCount()
    {
        return reportDownloadCount;
    }
    
    public void setReportDownloadCount(Integer reportDownloadCount)
    {
        this.reportDownloadCount = reportDownloadCount;
    }
    
    public String getReportPrintRequiredText()
    {
        
        return reportPrintRequiredText;
    }
    
    public void setReportPrintRequiredText(String reportPrintRequiredText)
    {
        this.reportPrintRequiredText = reportPrintRequiredText;
    }
    
    public String getStatusText()
    {
        return statusText;
    }
    
    public void setStatusText(String statusText)
    {
        this.statusText = statusText;
    }
    
    public String getSampleTypeText()
    {
        return sampleTypeText;
    }
    
    public void setSampleTypeText(String sampleTypeText)
    {
        this.sampleTypeText = sampleTypeText;
    }
    
    public List<OrderHistory> getOrderHistoryList()
    {
        return OrderHistoryList;
    }
    
    public void setOrderHistoryList(List<OrderHistory> orderHistoryList)
    {
        OrderHistoryList = orderHistoryList;
    }
    
    public byte[] getReportContents()
    {
        return reportContents;
    }
    
    public void setReportContents(byte[] reportContents)
    {
        this.reportContents = reportContents;
    }

    public String getVigilance() {
        return vigilance;
    }

    public void setVigilance(String vigilance) {
        this.vigilance = vigilance;
    }
}
