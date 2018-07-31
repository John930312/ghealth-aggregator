package com.todaysoft.ghealth.wechat.service.wechat.dto;

import org.springframework.util.StringUtils;

import com.fasterxml.jackson.annotation.JsonProperty;

public class WechatDTO
{
    @JsonProperty("errcode")
    private String errorCode;
    
    @JsonProperty("errmsg")
    private String errorMessage;
    
    public boolean isValid()
    {
        return StringUtils.isEmpty(errorCode) || "0".equals(errorCode);
    }
    
    public String getErrorCode()
    {
        return errorCode;
    }
    
    public void setErrorCode(String errorCode)
    {
        this.errorCode = errorCode;
    }
    
    public String getErrorMessage()
    {
        return errorMessage;
    }
    
    public void setErrorMessage(String errorMessage)
    {
        this.errorMessage = errorMessage;
    }
}
