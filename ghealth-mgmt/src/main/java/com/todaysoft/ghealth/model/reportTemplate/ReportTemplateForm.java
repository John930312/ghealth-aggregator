package com.todaysoft.ghealth.model.reportTemplate;

import org.springframework.web.multipart.MultipartFile;

public class ReportTemplateForm
{
    private String id;
    
    private String name;
    
    private String productId;
    
    private boolean customized;
    
    private String agencyId;
    
    private MultipartFile file;
    
    private String description;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
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
    
    public MultipartFile getFile()
    {
        return file;
    }
    
    public void setFile(MultipartFile file)
    {
        this.file = file;
    }
    
    public String getDescription()
    {
        return description;
    }
    
    public void setDescription(String description)
    {
        this.description = description;
    }
}
