package com.todaysoft.ghealth.mgmt.model;

public class OrderReportStreamDTO
{
    private String suffix;
    
    private byte[] content;
    
    public String getSuffix()
    {
        return suffix;
    }
    
    public void setSuffix(String suffix)
    {
        this.suffix = suffix;
    }
    
    public byte[] getContent()
    {
        return content;
    }
    
    public void setContent(byte[] content)
    {
        this.content = content;
    }
}
