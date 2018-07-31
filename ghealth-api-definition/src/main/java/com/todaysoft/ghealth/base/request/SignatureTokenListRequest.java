package com.todaysoft.ghealth.base.request;

import java.util.Map;

public abstract class SignatureTokenListRequest extends SignatureTokenRequest
{
    private Integer pageNo;
    
    private Integer pageSize;
    
    @Override
    protected void setSignFields(Map<String, String> fields)
    {
        super.setSignFields(fields);
        fields.put("pageNo", null == pageNo ? "" : pageNo.toString());
        fields.put("pageSize", null == pageSize ? "" : pageSize.toString());
    }
    
    public Integer getPageNo()
    {
        return pageNo;
    }
    
    public void setPageNo(Integer pageNo)
    {
        this.pageNo = pageNo;
    }
    
    public Integer getPageSize()
    {
        return pageSize;
    }
    
    public void setPageSize(Integer pageSize)
    {
        this.pageSize = pageSize;
    }
}
