package com.todaysoft.ghealth.wechat.service;

public class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = -2507236132032711662L;
    
    private String error;
    
    public ServiceException(String error)
    {
        this.error = error;
    }
    
    public ServiceException(String error, String message)
    {
        super(message);
        this.error = error;
    }
    
    public String getError()
    {
        return error;
    }
}
