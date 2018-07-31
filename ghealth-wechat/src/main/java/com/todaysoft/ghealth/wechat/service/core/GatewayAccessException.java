package com.todaysoft.ghealth.wechat.service.core;

public class GatewayAccessException extends RuntimeException
{
    private static final long serialVersionUID = -4946588710822763338L;
    
    private static final String TYPE_UNDEFINED = "UNDEFINED";
    
    private static final String TYPE_UNREACHABLE = "UNREACHABLE";
    
    private static final String TYPE_BUSINESS_ERROR = "BUSINESS_ERROR";
    
    private String errorType;
    
    private String errorCode;
    
    private GatewayAccessException(String errorType, String errorCode, String message)
    {
        super(message);
        this.errorType = errorType;
        this.errorCode = errorCode;
    }
    
    public static GatewayAccessException undefined(String message)
    {
        return new GatewayAccessException(TYPE_UNDEFINED, null, message);
    }
    
    public static GatewayAccessException unreachable(String message)
    {
        return new GatewayAccessException(TYPE_UNREACHABLE, null, message);
    }
    
    public static GatewayAccessException businessError(String errorCode)
    {
        return businessError(errorCode, null);
    }
    
    public static GatewayAccessException businessError(String errorCode, String message)
    {
        return new GatewayAccessException(TYPE_BUSINESS_ERROR, errorCode, message);
    }
    
    public String getErrorType()
    {
        return errorType;
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
}
