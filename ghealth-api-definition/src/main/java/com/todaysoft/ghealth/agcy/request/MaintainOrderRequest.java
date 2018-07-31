package com.todaysoft.ghealth.agcy.request;

import java.math.BigDecimal;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import com.todaysoft.ghealth.base.response.model.Customer;
import com.todaysoft.ghealth.base.response.model.Product;

/**
 * Created by xjw on 2017/9/13.
 */
public class MaintainOrderRequest extends SignatureTokenRequest
{
    
    private String id;
    
    private String code;
    
    private Customer customer;
    
    private Product product;
    
    private String status;
    
    private String statusText;
    
    private BigDecimal price;
    
    private Integer reportPrintRequired;
    
    private String sampleType;
    
    private String agencyId;
    
    private String agencyAccountId;
    
    private BigDecimal actualPrice;
    
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
    
    public BigDecimal getPrice()
    {
        return price;
    }
    
    public void setPrice(BigDecimal price)
    {
        this.price = price;
    }
    
    public Customer getCustomer()
    {
        return customer;
    }
    
    public void setCustomer(Customer customer)
    {
        this.customer = customer;
    }
    
    public Product getProduct()
    {
        return product;
    }
    
    public void setProduct(Product product)
    {
        this.product = product;
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
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getAgencyAccountId()
    {
        return agencyAccountId;
    }
    
    public void setAgencyAccountId(String agencyAccountId)
    {
        this.agencyAccountId = agencyAccountId;
    }
    
    public BigDecimal getActualPrice()
    {
        return actualPrice;
    }
    
    public void setActualPrice(BigDecimal actualPrice)
    {
        this.actualPrice = actualPrice;
    }
}
