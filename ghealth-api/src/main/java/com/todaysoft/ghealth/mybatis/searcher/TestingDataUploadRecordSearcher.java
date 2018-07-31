package com.todaysoft.ghealth.mybatis.searcher;

import java.util.Date;

public class TestingDataUploadRecordSearcher
{
    private String uploaderName;
    
    private Date uploadTimeStart;
    
    private Date uploadTimeEnd;
    
    private Integer offset;
    
    private Integer limit;
    
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
    
    public Integer getOffset()
    {
        return offset;
    }
    
    public void setOffset(Integer offset)
    {
        this.offset = offset;
    }
    
    public Integer getLimit()
    {
        return limit;
    }
    
    public void setLimit(Integer limit)
    {
        this.limit = limit;
    }
}
