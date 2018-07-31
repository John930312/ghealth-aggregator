package com.todaysoft.ghealth.model.product;

import java.math.BigDecimal;
import java.util.Set;

public class TestingProductForm
{
    private String id;
    
    private String code;
    
    private String name;
    
    private BigDecimal guidingPrice;
    
    private String sexRestraint;
    
    private boolean enabled;
    
    private Set<String> testingItems;
    
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
    
    public String getName()
    {
        return name;
    }
    
    public void setName(String name)
    {
        this.name = name;
    }
    
    public BigDecimal getGuidingPrice()
    {
        return guidingPrice;
    }
    
    public void setGuidingPrice(BigDecimal guidingPrice)
    {
        this.guidingPrice = guidingPrice;
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
