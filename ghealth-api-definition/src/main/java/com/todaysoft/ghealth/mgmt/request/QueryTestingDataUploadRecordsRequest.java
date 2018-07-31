package com.todaysoft.ghealth.mgmt.request;

import java.util.Map;

import com.todaysoft.ghealth.base.request.SignatureTokenListRequest;

public class QueryTestingDataUploadRecordsRequest extends SignatureTokenListRequest
{
    private String uploaderName;
    
    private Long uploadTimeStart;
    
    private Long uploadTimeEnd;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        fields.put("uploaderName", uploaderName);
        fields.put("uploadTimeStart", null == uploadTimeStart ? "" : String.valueOf(uploadTimeStart));
        fields.put("uploadTimeEnd", null == uploadTimeEnd ? "" : String.valueOf(uploadTimeEnd));
    }
    
    public String getUploaderName()
    {
        return uploaderName;
    }
    
    public void setUploaderName(String uploaderName)
    {
        this.uploaderName = uploaderName;
    }
    
    public Long getUploadTimeStart()
    {
        return uploadTimeStart;
    }
    
    public void setUploadTimeStart(Long uploadTimeStart)
    {
        this.uploadTimeStart = uploadTimeStart;
    }
    
    public Long getUploadTimeEnd()
    {
        return uploadTimeEnd;
    }
    
    public void setUploadTimeEnd(Long uploadTimeEnd)
    {
        this.uploadTimeEnd = uploadTimeEnd;
    }
}
