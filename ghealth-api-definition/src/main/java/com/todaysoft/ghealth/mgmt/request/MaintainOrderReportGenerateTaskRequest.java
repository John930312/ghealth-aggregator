package com.todaysoft.ghealth.mgmt.request;

import com.todaysoft.ghealth.base.request.SignatureTokenRequest;

import java.util.Map;

public class MaintainOrderReportGenerateTaskRequest extends SignatureTokenRequest
{
    private String id;
    
    private Integer status;
    
    private String creatorName;
    
    private String wordFileUrl;
    
    private String pdfFileUrl;
    
    private String orderIds;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("id", id);
        fields.put("orderIds", orderIds);
        fields.put("status", String.valueOf(status));
        fields.put("wordFileUrl", wordFileUrl);
        fields.put("pdfFileUrl", pdfFileUrl);
    }
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public Integer getStatus()
    {
        return status;
    }
    
    public void setStatus(Integer status)
    {
        this.status = status;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public String getWordFileUrl()
    {
        return wordFileUrl;
    }
    
    public void setWordFileUrl(String wordFileUrl)
    {
        this.wordFileUrl = wordFileUrl;
    }
    
    public String getPdfFileUrl()
    {
        return pdfFileUrl;
    }
    
    public void setPdfFileUrl(String pdfFileUrl)
    {
        this.pdfFileUrl = pdfFileUrl;
    }
    
    public String getOrderIds()
    {
        return orderIds;
    }
    
    public void setOrderIds(String orderIds)
    {
        this.orderIds = orderIds;
    }
}
