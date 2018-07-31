package com.todaysoft.ghealth.base.response.model;

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
    
    private Long billTime;
    
    private Order order;
    
    private String incomeExpenses;
    
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
    
    public BigDecimal getAmountAfter()
    {
        return amountAfter;
    }
    
    public void setAmountAfter(BigDecimal amountAfter)
    {
        this.amountAfter = amountAfter;
    }
    
    public Long getBillTime()
    {
        return billTime;
    }
    
    public void setBillTime(Long billTime)
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
    
    public String getIncomeExpenses()
    {
        return incomeExpenses;
    }
    
    public void setIncomeExpenses(String incomeExpenses)
    {
        this.incomeExpenses = incomeExpenses;
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