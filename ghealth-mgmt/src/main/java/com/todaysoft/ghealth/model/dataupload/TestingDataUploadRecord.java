package com.todaysoft.ghealth.model.dataupload;

import java.util.Date;

public class TestingDataUploadRecord
{
    private String id;
    
    private String title;
    
    private String filename;
    
    private String fileUri;
    
    private Date uploadTime;
    
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
    
    public Date getUploadTime()
    {
        return uploadTime;
    }
    
    public void setUploadTime(Date uploadTime)
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
