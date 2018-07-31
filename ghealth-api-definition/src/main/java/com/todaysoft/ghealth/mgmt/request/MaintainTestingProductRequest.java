package com.todaysoft.ghealth.mgmt.request;

import java.math.BigDecimal;
import java.util.Map;
import java.util.Set;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainTestingProductRequest extends SignatureTokenRequest
{
    private String id;
    
    private String code;
    
    private String name;
    
    private BigDecimal guidingPrice;
    
    private String sexRestraint;
    
    private boolean enabled;
    
    private Set<String> testingItems;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("code", code);
        fields.put("name", name);
        fields.put("guidingPrice", String.valueOf(guidingPrice));
        fields.put("sexRestraint", sexRestraint);
        fields.put("enabled", String.valueOf(enabled));
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public BigDecimal getGuidingPrice()
    {
        return guidingPrice;
    }
    
    public void setGuidingPrice(BigDecimal guidingPrice)
    {
        this.guidingPrice = guidingPrice;
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
    
    public String getSexRestraint()
    {
        return sexRestraint;
    }
    
    public void setSexRestraint(String sexRestraint)
    {
        this.sexRestraint = sexRestraint;
    }
    
    public boolean isEnabled()
    {
        return enabled;
    }
    
    public void setEnabled(boolean enabled)
    {
        this.enabled = enabled;
    }
    
    public Set<String> getTestingItems()
    {
        return testingItems;
    }
    
    public void setTestingItems(Set<String> testingItems)
    {
        this.testingItems = testingItems;
    }
}
