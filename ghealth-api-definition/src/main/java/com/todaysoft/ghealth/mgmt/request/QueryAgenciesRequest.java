package com.todaysoft.ghealth.mgmt.request;

import java.math.BigDecimal;
import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryAgenciesRequest extends SignatureTokenListRequest
{
    private String code;
    
    private String name;
    
    private String productId;

    private BigDecimal minAmount;

    private BigDecimal maxAmount;

    private String primaryUsername;

    private String abbr;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("code", code);
        fields.put("primaryUsername", primaryUsername);
        fields.put("abbr", abbr);
        fields.put("name", name);
        fields.put("productId", productId);
    }

    public BigDecimal getMinAmount() {
        return minAmount;
    }

    public void setMinAmount(BigDecimal minAmount) {
        this.minAmount = minAmount;
    }

    public BigDecimal getMaxAmount() {
        return maxAmount;
    }

    public void setMaxAmount(BigDecimal maxAmount) {
        this.maxAmount = maxAmount;
    }

    public String getCode()
    {
        return code;
    }
    
    public void setCode(String code)
    {
        this.code = code;
    }
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }

    public String getPrimaryUsername() {
        return primaryUsername;
    }

    public void setPrimaryUsername(String primaryUsername) {
        this.primaryUsername = primaryUsername;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }
}