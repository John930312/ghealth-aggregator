package com.todaysoft.ghealth.open.api.mybatis.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @Author: xjw
 * @Date: 2018/9/4 10:36
 */
public class OrderRequest
{
    private String id;
    
    private Product product;
    
    private Customer customer;
    
    private Agency agency;
    
    private String code;
    
    private BigDecimal actualPrice;
    
    private String status;
    
    private Date submitTime;
    
    private String submitorName;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date updateTime;
    
    private String updatorName;
    
    private Date operateTime;
    
    private Boolean deleted;
    
    private Date deleteTime;
    
    private String deletorName;
    
    private Integer reportPrintRequired;
    
    private Integer reportDownloadCount;
    
    private String sampleType;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
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
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
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
    
    public Date getUpdateTime()
    {
        return updateTime;
    }
    
    public void setUpdateTime(Date updateTime)
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
    
    public Date getOperateTime()
    {
        return operateTime;
    }
    
    public void setOperateTime(Date operateTime)
    {
        this.operateTime = operateTime;
    }
    
    public Boolean getDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(Boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public Date getDeleteTime()
    {
        return deleteTime;
    }
    
    public void setDeleteTime(Date deleteTime)
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
    
    public Integer getReportPrintRequired()
    {
        return reportPrintRequired;
    }
    
    public void setReportPrintRequired(Integer reportPrintRequired)
    {
        this.reportPrintRequired = reportPrintRequired;
    }
    
    public Integer getReportDownloadCount()
    {
        return reportDownloadCount;
    }
    
    public void setReportDownloadCount(Integer reportDownloadCount)
    {
        this.reportDownloadCount = reportDownloadCount;
    }
    
    public String getSampleType()
    {
        return sampleType;
    }
    
    public void setSampleType(String sampleType)
    {
        this.sampleType = sampleType;
    }
}
