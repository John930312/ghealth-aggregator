package com.todaysoft.ghealth.mgmt.model;

public class ReportGenerateTaskDTO
{
    private String id;
    
    private String status;
    
    private Long finishTime;
    
    private String errorCode;
    
    private String errorMessage;
    
    private String pdfFileUri;
    
    private String wordFileUri;
    
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
    
    public Long getFinishTime()
    {
        return finishTime;
    }
    
    public void setFinishTime(Long finishTime)
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
    
    public String getPdfFileUri()
    {
        return pdfFileUri;
    }
    
    public void setPdfFileUri(String pdfFileUri)
    {
        this.pdfFileUri = pdfFileUri;
    }
    
    public String getWordFileUri()
    {
        return wordFileUri;
    }
    
    public void setWordFileUri(String wordFileUri)
    {
        this.wordFileUri = wordFileUri;
    }
}
