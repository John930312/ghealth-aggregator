package com.todaysoft.ghealth.base.response;

public class PagerResponse<T>
{
    private Pager<T> data;
    
    public PagerResponse()
    {
        
    }
    
    public PagerResponse(Pager<T> data)
    {
        this.data = data;
    }
    
    public Pager<T> getData()
    {
        return data;
    }
    
    public void setData(Pager<T> data)
    {
        this.data = data;
    }
}
