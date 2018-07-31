package com.todaysoft.ghealth.model.dataupload;

public class TestingDataUploadHeader<T>
{
    private T data;
    
    private String text;
    
    private boolean valid;
    
    private String message;
    
    public T getData()
    {
        return data;
    }
    
    public void setData(T data)
    {
        this.data = data;
    }
    
    public String getText()
    {
        return text;
    }
    
    public void setText(String text)
    {
        this.text = text;
    }
    
    public boolean isValid()
    {
        return valid;
    }
    
    public void setValid(boolean valid)
    {
        this.valid = valid;
    }
    
    public String getMessage()
    {
        return message;
    }
    
    public void setMessage(String message)
    {
        this.message = message;
    }
}
