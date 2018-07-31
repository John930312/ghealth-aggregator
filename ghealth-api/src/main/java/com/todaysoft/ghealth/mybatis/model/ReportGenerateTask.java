package com.todaysoft.ghealth.mybatis.model;

import java.util.Date;

public class ReportGenerateTask
{
    public static final String STATUS_PROCESSING = "0";
    
    public static final String STATUS_SUCCESS = "1";
    
    public static final String STATUS_FAILURE = "2";
    
    private String id;
    
    private String status;
    
    private Date createTime;
    
    private String creatorName;
    
    private Date finishTime;
    
    private String errorCode;
    
    private String errorMessage;
    
    private String wordFileUrl;
    
    private String pdfFileUrl;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getStatus()
    {
        return status;
    }
    
    public void setStatus(String status)
    {
        this.status = status;
    }
    
    public Date getCreateTime()
    {
        return createTime;
    }
    
    public void setCreateTime(Date createTime)
    {
        this.createTime = createTime;
    }
    
    public String getCreatorName()
    {
        return creatorName;
    }
    
    public void setCreatorName(String creatorName)
    {
        this.creatorName = creatorName;
    }
    
    public Date getFinishTime()
    {
        return finishTime;
    }
    
    public void setFinishTime(Date finishTime)
    {
        this.finishTime = finishTime;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
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
}