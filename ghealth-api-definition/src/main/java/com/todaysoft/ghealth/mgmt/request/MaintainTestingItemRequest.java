package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class MaintainTestingItemRequest extends SignatureTokenRequest
{
    private String id;
    
    private String code;
    
    private String name;
    
    private String category;
    
    private String categoryMapping;
    
    private String sexRestraint;
    
    private boolean enabled;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("code", code);
        fields.put("name", name);
        fields.put("category", category);
        fields.put("categoryMapping", categoryMapping);
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
    
    public String getCategory()
    {
        return category;
    }
    
    public void setCategory(String category)
    {
        this.category = category;
    }
    
    public String getCategoryMapping()
    {
        return categoryMapping;
    }
    
    public void setCategoryMapping(String categoryMapping)
    {
        this.categoryMapping = categoryMapping;
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
}
