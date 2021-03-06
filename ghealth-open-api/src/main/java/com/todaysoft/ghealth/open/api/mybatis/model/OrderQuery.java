package com.todaysoft.ghealth.open.api.mybatis.model;

import java.util.Date;
import java.util.List;

public class OrderQuery extends Query
{
    private List<String> keyIncludes;

    private List<String> keyExcludes;

    private String orderCode;

    private List<String> orderStatusIncludes;

    private List<String> orderStatusExcludes;

    private String productName;

    private List<String> productKeyIncludes;

    private List<String> productKeyExcludes;

    private String customerName;

    private String agencyName;

    private List<String> agencyIncludes;

    private List<String> agencyExcludes;

    private Date reportGenerateTimeStart;

    private Date reportGenerateTimeEnd;

    public List<String> getKeyIncludes()
    {
        return keyIncludes;
    }

    public void setKeyIncludes(List<String> keyIncludes)
    {
        this.keyIncludes = keyIncludes;
    }

    public List<String> getKeyExcludes()
    {
        return keyExcludes;
    }

    public void setKeyExcludes(List<String> keyExcludes)
    {
        this.keyExcludes = keyExcludes;
    }

    public String getOrderCode()
    {
        return orderCode;
    }

    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }

    public List<String> getOrderStatusIncludes()
    {
        return orderStatusIncludes;
    }

    public void setOrderStatusIncludes(List<String> orderStatusIncludes)
    {
        this.orderStatusIncludes = orderStatusIncludes;
    }

    public List<String> getOrderStatusExcludes()
    {
        return orderStatusExcludes;
    }

    public void setOrderStatusExcludes(List<String> orderStatusExcludes)
    {
        this.orderStatusExcludes = orderStatusExcludes;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public List<String> getProductKeyIncludes()
    {
        return productKeyIncludes;
    }

    public void setProductKeyIncludes(List<String> productKeyIncludes)
    {
        this.productKeyIncludes = productKeyIncludes;
    }

    public List<String> getProductKeyExcludes()
    {
        return productKeyExcludes;
    }

    public void setProductKeyExcludes(List<String> productKeyExcludes)
    {
        this.productKeyExcludes = productKeyExcludes;
    }

    public String getCustomerName()
    {
        return customerName;
    }

    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }

    public String getAgencyName()
    {
        return agencyName;
    }

    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }

    public List<String> getAgencyIncludes()
    {
        return agencyIncludes;
    }

    public void setAgencyIncludes(List<String> agencyIncludes)
    {
        this.agencyIncludes = agencyIncludes;
    }

    public List<String> getAgencyExcludes()
    {
        return agencyExcludes;
    }

    public void setAgencyExcludes(List<String> agencyExcludes)
    {
        this.agencyExcludes = agencyExcludes;
    }

    public Date getReportGenerateTimeStart()
    {
        return reportGenerateTimeStart;
    }

    public void setReportGenerateTimeStart(Date reportGenerateTimeStart)
    {
        this.reportGenerateTimeStart = reportGenerateTimeStart;
    }

    public Date getReportGenerateTimeEnd()
    {
        return reportGenerateTimeEnd;
    }

    public void setReportGenerateTimeEnd(Date reportGenerateTimeEnd)
    {
        this.reportGenerateTimeEnd = reportGenerateTimeEnd;
    }
}
