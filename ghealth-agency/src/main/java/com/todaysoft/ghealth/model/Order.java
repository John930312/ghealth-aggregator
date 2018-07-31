package com.todaysoft.ghealth.model;

import java.math.BigDecimal;
import java.util.Date;

import com.todaysoft.ghealth.base.response.model.Agency;
import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.base.response.model.Product;
import com.todaysoft.ghealth.model.product.AgencyProductDetails;
import com.todaysoft.ghealth.utils.ExcelField;

public class Order
{
    private String id;
    
    private String code;
    
    private Customer customer;
    
    private Product product;
    
    private AgencyProductDetails agencyProductDetails;
    
    private Agency agency;
    
    private String status;
    
    private String statusText;
    
    private BigDecimal actualPrice;
    
    private String creatorId;
    
    private Date createTime;
    
    private Date submitTime;
    
    private String creatorName;
    
    private boolean deleted;
    
    private String remark;
    
    private String reasonType;
    
    private Integer reportPrintRequired;
    
    private String sampleType;
    
    private String reportGenerateTaskId;
    
    private String agencyAccountId;
    
    private BigDecimal price;
    
    //冗余字段
    private String customerName;
    
    private String productName;
    
    private String agencyName;
    
    private String testName;
    
    private Boolean havePDF;
    
    private Boolean haveWord;

    private Date reportGenerateTime;

    private Date operateTime;

    private Integer reportDownloadCount;

    private Boolean monthAgo;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    @ExcelField(title = "订单编号", align = 2, sort = 1)
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
    
    public String getCreatorId()
    {
        return creatorId;
    }
    
    public void setCreatorId(String creatorId)
    {
        this.creatorId = creatorId;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    @ExcelField(title = "提交时间", align = 2, sort = 7)
    public Date getSubmitTime()
    {
        return submitTime;
    }
    
    public void setSubmitTime(Date submitTime)
    {
        this.submitTime = submitTime;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
    }
    
    @ExcelField(title = "客户姓名", align = 2, sort = 2)
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    @ExcelField(title = "检测产品", align = 2, sort = 3)
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @ExcelField(title = "代理商", align = 2, sort = 4)
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    @ExcelField(title = "检测机构", align = 2, sort = 5)
    public String getTestName()
    {
        return testName;
    }
    
    public void setTestName(String testName)
    {
        this.testName = testName;
    }
    
    @ExcelField(title = "创建人", align = 2, sort = 6)
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public boolean isDeleted()
    {
        return deleted;
    }
    
    public void setDeleted(boolean deleted)
    {
        this.deleted = deleted;
    }
    
    public String getRemark()
    {
        return remark;
    }
    
    public void setRemark(String remark)
    {
        this.remark = remark;
    }
    
    public String getReasonType()
    {
        return reasonType;
    }
    
    public void setReasonType(String reasonType)
    {
        this.reasonType = reasonType;
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
    
    public AgencyProductDetails getAgencyProductDetails()
    {
        return agencyProductDetails;
    }
    
    public void setAgencyProductDetails(AgencyProductDetails agencyProductDetails)
    {
        this.agencyProductDetails = agencyProductDetails;
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
    
    public String getAgencyAccountId()
    {
        return agencyAccountId;
    }
    
    public void setAgencyAccountId(String agencyAccountId)
    {
        this.agencyAccountId = agencyAccountId;
    }
    
    public BigDecimal getPrice()
    {
        return price;
    }
    
    public void setPrice(BigDecimal price)
    {
        this.price = price;
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

    public Date getReportGenerateTime() {
        return reportGenerateTime;
    }

    public void setReportGenerateTime(Date reportGenerateTime) {
        this.reportGenerateTime = reportGenerateTime;
    }

    public Date getOperateTime() {
        return operateTime;
    }

    public void setOperateTime(Date operateTime) {
        this.operateTime = operateTime;
    }

    public Integer getReportDownloadCount() {
        return reportDownloadCount;
    }

    public void setReportDownloadCount(Integer reportDownloadCount) {
        this.reportDownloadCount = reportDownloadCount;
    }

    public Boolean getMonthAgo() {
        return monthAgo;
    }

    public void setMonthAgo(Boolean monthAgo) {
        this.monthAgo = monthAgo;
    }
}
