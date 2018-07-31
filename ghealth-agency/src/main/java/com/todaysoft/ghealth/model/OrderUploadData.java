package com.todaysoft.ghealth.model;

import java.util.List;
import java.util.Map;

public class OrderUploadData
{
    private Integer uploadCount;
    
    private Integer effectiveUploadCount;
    
    private Integer invalidUploadCount;
    
    private List<String> heads;
    
    private List<Map<String, OrderUploadModel>> datas;
    
    public Integer getUploadCount()
    {
        return uploadCount;
    }
    
    public void setUploadCount(Integer uploadCount)
    {
        this.uploadCount = uploadCount;
    }
    
    public Integer getEffectiveUploadCount()
    {
        return effectiveUploadCount;
    }
    
    public void setEffectiveUploadCount(Integer effectiveUploadCount)
    {
        this.effectiveUploadCount = effectiveUploadCount;
    }
    
    public Integer getInvalidUploadCount()
    {
        return invalidUploadCount;
    }
    
    public void setInvalidUploadCount(Integer invalidUploadCount)
    {
        this.invalidUploadCount = invalidUploadCount;
    }
    
    public List<String> getHeads()
    {
        return heads;
    }
    
    public void setHeads(List<String> heads)
    {
        this.heads = heads;
    }
    
    public List<Map<String, OrderUploadModel>> getDatas()
    {
        return datas;
    }
    
    public void setDatas(List<Map<String, OrderUploadModel>> datas)
    {
        this.datas = datas;
    }
}
