package com.todaysoft.ghealth.model.agency;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.utils.ExcelField;

import java.math.BigDecimal;
import java.util.Date;

public class AgencyBill
{
    private String id;
    
    private Agency agency;
    
    private String title;
    
    private Boolean increased;
    
    private String billType;
    
    private String eventDetails;
    
    private BigDecimal amountBefore;
    
    private BigDecimal amountAfter;
    
    private Date billTime;
    
    private Order order;
    
    //下载  冗余字段
    private String incomeExpenses;
    
    private String productName;
    
    private String agencyName;
    
    private String dealOrder;
    
    private String operateName;

    private String rechargeType;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Agency getAgency()
    {
        return agency;
    }
    
    public void setAgency(Agency agency)
    {
        this.agency = agency;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public Boolean getIncreased()
    {
        return increased;
    }
    
    public void setIncreased(Boolean increased)
    {
        this.increased = increased;
    }
    
    public String getBillType()
    {
        return billType;
    }
    
    public void setBillType(String billType)
    {
        this.billType = billType;
    }
    
    public String getEventDetails()
    {
        return eventDetails;
    }
    
    public void setEventDetails(String eventDetails)
    {
        this.eventDetails = eventDetails;
    }
    
    public BigDecimal getAmountBefore()
    {
        return amountBefore;
    }
    
    public void setAmountBefore(BigDecimal amountBefore)
    {
        this.amountBefore = amountBefore;
    }
    
    @ExcelField(title = "余额", align = 2, sort = 3)
    public BigDecimal getAmountAfter()
    {
        return amountAfter;
    }
    
    public void setAmountAfter(BigDecimal amountAfter)
    {
        this.amountAfter = amountAfter;
    }
    
    @ExcelField(title = "操作时间", align = 2, sort = 1)
    public Date getBillTime()
    {
        return billTime;
    }
    
    public void setBillTime(Date billTime)
    {
        this.billTime = billTime;
    }
    
    public Order getOrder()
    {
        return order;
    }
    
    public void setOrder(Order order)
    {
        this.order = order;
    }
    
    @ExcelField(title = "收支", align = 2, sort = 2)
    public String getIncomeExpenses()
    {
        return incomeExpenses;
    }
    
    public void setIncomeExpenses(String incomeExpenses)
    {
        this.incomeExpenses = incomeExpenses;
    }
    
    @ExcelField(title = "产品名称", align = 2, sort = 4)
    public String getProductName()
    {
        return productName;
    }
    
    public void setProductName(String productName)
    {
        this.productName = productName;
    }
    
    @ExcelField(title = "代理商", align = 2, sort = 5)
    public String getAgencyName()
    {
        return agencyName;
    }
    
    public void setAgencyName(String agencyName)
    {
        this.agencyName = agencyName;
    }
    
    @ExcelField(title = "交易订单", align = 2, sort = 6)
    public String getDealOrder()
    {
        return dealOrder;
    }
    
    public void setDealOrder(String dealOrder)
    {
        this.dealOrder = dealOrder;
    }
    
    public String getOperateName()
    {
        return operateName;
    }
    
    public void setOperateName(String operateName)
    {
        this.operateName = operateName;
    }

    public String getRechargeType() {
        return rechargeType;
    }

    public void setRechargeType(String rechargeType) {
        this.rechargeType = rechargeType;
    }
}