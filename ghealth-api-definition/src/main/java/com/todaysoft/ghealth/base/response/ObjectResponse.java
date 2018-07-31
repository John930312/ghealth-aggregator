package com.todaysoft.ghealth.base.response;

public class ObjectResponse<T>
{
    private T data;
    
    public ObjectResponse()
    {
        
    }
    
    public ObjectResponse(T data)
    {
        this.data = data;
    }
    
    public T getData()
    {
        return data;
    }
    
    public void setData(T data)
    {
        this.data = data;
    }
}
