package com.todaysoft.ghealth.open.api.restful.model;

import java.math.BigDecimal;

public class OrderDTO
{
    private String id;

    private String code;

    private AgencyDTO agency;

    private ProductDTO product;

    private CustomerDTO customer;

    private BigDecimal amount;

    private String sampleType;

    private boolean printReport;

    private String submitTime;

    private String submitorName;

    private String status;

    private String createTime;

    private String creatorName;

    private String reportGenerateTime;

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

    public AgencyDTO getAgency()
    {
        return agency;
    }

    public void setAgency(AgencyDTO agency)
    {
        this.agency = agency;
    }

    public ProductDTO getProduct()
    {
        return product;
    }

    public void setProduct(ProductDTO product)
    {
        this.product = product;
    }

    public CustomerDTO getCustomer()
    {
        return customer;
    }

    public void setCustomer(CustomerDTO customer)
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

    public String getSubmitTime()
    {
        return submitTime;
    }

    public void setSubmitTime(String submitTime)
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

    public String getCreateTime()
    {
        return createTime;
    }

    public void setCreateTime(String createTime)
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

    public String getReportGenerateTime()
    {
        return reportGenerateTime;
    }

    public void setReportGenerateTime(String reportGenerateTime)
    {
        this.reportGenerateTime = reportGenerateTime;
    }
}
