package com.todaysoft.ghealth.mgmt.product.report.template;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

public class ReportTemplateMaintainRequest extends SignatureTokenRequest
{
    private String id;
    
    private String templateName;
    
    private String productId;
    
    private boolean customized;
    
    private String agencyId;
    
    private String description;
    
    private String templateKey;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTemplateName()
    {
        return templateName;
    }
    
    public void setTemplateName(String templateName)
    {
        this.templateName = templateName;
    }
    
    public String getProductId()
    {
        return productId;
    }
    
    public void setProductId(String productId)
    {
        this.productId = productId;
    }
    
    public boolean isCustomized()
    {
        return customized;
    }
    
    public void setCustomized(boolean customized)
    {
        this.customized = customized;
    }
    
    public String getAgencyId()
    {
        return agencyId;
    }
    
    public void setAgencyId(String agencyId)
    {
        this.agencyId = agencyId;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
    
    public String getTemplateKey()
    {
        return templateKey;
    }
    
    public void setTemplateKey(String templateKey)
    {
        this.templateKey = templateKey;
    }
}
