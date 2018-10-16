package com.todaysoft.ghealth.mybatis.searcher;

/**
 * Created by xjw on 2017/9/12.
 */
public class OrderSearcher
{
    private String id;
    
    private String agencyName;
    
    private String customerName;
    
    private String status;
    
    private String orderCode;
    
    private String productId;
    
    private String productName;
    
    private String startCreateTime;
    
    private String endStartTime;

    private String startReportTime;

    private String endReportTime;
    
    private String customerId;
    
    private String agencyId;
    
    private Integer offset;
    
    private Integer limit;
    
    private boolean orderCodeExactMatches;
    
    private String createName;

    private String primaryUsername;

    private String customerPhone;
    
    private String vigilance;
    
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    public String getCustomerName()
    {
        return customerName;
    }
    
    public void setCustomerName(String customerName)
    {
        this.customerName = customerName;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public String getOrderCode()
    {
        return orderCode;
    }
    
    public void setOrderCode(String orderCode)
    {
        this.orderCode = orderCode;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public String getStartCreateTime()
    {
        return startCreateTime;
    }
    
    public void setStartCreateTime(String startCreateTime)
    {
        this.startCreateTime = startCreateTime;
    }
    
    public String getEndStartTime()
    {
        return endStartTime;
    }
    
    public void setEndStartTime(String endStartTime)
    {
        this.endStartTime = endStartTime;
    }
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getCustomerId()
    {
        return customerId;
    }
    
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
    
    public boolean isOrderCodeExactMatches()
    {
        return orderCodeExactMatches;
    }
    
    public void setOrderCodeExactMatches(boolean orderCodeExactMatches)
    {
        this.orderCodeExactMatches = orderCodeExactMatches;
    }
    
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getCreateName()
    {
        return createName;
    }
    
    public void setCreateName(String createName)
    {
        this.createName = createName;
    }

    public String getStartReportTime() {
        return startReportTime;
    }

    public void setStartReportTime(String startReportTime) {
        this.startReportTime = startReportTime;
    }

    public String getEndReportTime() {
        return endReportTime;
    }

    public void setEndReportTime(String endReportTime) {
        this.endReportTime = endReportTime;
    }

    public String getPrimaryUsername() {
        return primaryUsername;
    }

    public void setPrimaryUsername(String primaryUsername) {
        this.primaryUsername = primaryUsername;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }
    
    public String getVigilance()
    {
        return vigilance;
    }
    
    public void setVigilance(String vigilance)
    {
        this.vigilance = vigilance;
    }
}
