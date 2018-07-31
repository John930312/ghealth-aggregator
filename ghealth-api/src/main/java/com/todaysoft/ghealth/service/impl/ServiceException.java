package com.todaysoft.ghealth.service.impl;

public class ServiceException extends RuntimeException
{
    private static final long serialVersionUID = -2507236132032711662L;
    
    private String errorCode;
    
    public ServiceException(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public ServiceException(String errorCode, Throwable cause)
    {
        super(cause);
        this.errorCode = errorCode;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
}
