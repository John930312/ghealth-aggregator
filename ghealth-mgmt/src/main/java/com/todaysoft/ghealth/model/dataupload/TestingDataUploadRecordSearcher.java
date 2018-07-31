package com.todaysoft.ghealth.model.dataupload;

import java.util.Date;

public class TestingDataUploadRecordSearcher
{
    private String uploaderName;
    
    private Date uploadTimeStart;
    
    private Date uploadTimeEnd;
    
    public String getUploaderName()
    {
        return uploaderName;
    }
    
    public void setUploaderName(String uploaderName)
    {
        this.uploaderName = uploaderName;
    }
    
    public Date getUploadTimeStart()
    {
        return uploadTimeStart;
    }
    
    public void setUploadTimeStart(Date uploadTimeStart)
    {
        this.uploadTimeStart = uploadTimeStart;
    }
    
    public Date getUploadTimeEnd()
    {
        return uploadTimeEnd;
    }
    
    public void setUploadTimeEnd(Date uploadTimeEnd)
    {
        this.uploadTimeEnd = uploadTimeEnd;
    }
}
