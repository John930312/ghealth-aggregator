package com.todaysoft.ghealth.base.response.model;

public class TestingDataUploadRecordDTO
{
    private String id;
    
    private String title;
    
    private String filename;
    
    private String fileUri;
    
    private Long uploadTime;
    
    private String uploaderName;
    
    public String getId()
    {
        return id;
    }
    
    public void setId(String id)
    {
        this.id = id;
    }
    
    public String getTitle()
    {
        return title;
    }
    
    public void setTitle(String title)
    {
        this.title = title;
    }
    
    public String getFilename()
    {
        return filename;
    }
    
    public void setFilename(String filename)
    {
        this.filename = filename;
    }
    
    public String getFileUri()
    {
        return fileUri;
    }
    
    public void setFileUri(String fileUri)
    {
        this.fileUri = fileUri;
    }
    
    public Long getUploadTime()
    {
        return uploadTime;
    }
    
    public void setUploadTime(Long uploadTime)
    {
        this.uploadTime = uploadTime;
    }
    
    public String getUploaderName()
    {
        return uploaderName;
    }
    
    public void setUploaderName(String uploaderName)
    {
        this.uploaderName = uploaderName;
    }
}
