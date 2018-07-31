package com.todaysoft.ghealth.service.impl;

import org.springframework.util.StringUtils;

public class ErrorCode
{
    public static final String UNDEFINED_ERROR = "00001";
    
    public static final String API_UNREACHABLE = "00002";
    
    public static final String fromGateway(String errorCode)
    {
        if (StringUtils.isEmpty(errorCode))
        {
            return UNDEFINED_ERROR;
        }
        
        errorCode = errorCode.replaceAll("AGCY_", "");
        return errorCode;
    }
}
