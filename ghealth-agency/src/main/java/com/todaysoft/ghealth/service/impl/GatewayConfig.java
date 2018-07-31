package com.todaysoft.ghealth.service.impl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:application-context.properties"})
public class GatewayConfig
{
    @Value("${ghealth.api.host}")
    private String apiHost;
    
    @Value("${ghealth.api.port}")
    private String apiPort;
    
    @Value("${ghealth.api.name}")
    private String apiName;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    public String getApiHost()
    {
        return apiHost;
    }
    
    public void setApiHost(String apiHost)
    {
        this.apiHost = apiHost;
    }
    
    public String getApiPort()
    {
        return apiPort;
    }
    
    public void setApiPort(String apiPort)
    {
        this.apiPort = apiPort;
    }
    
    public String getApiName()
    {
        return apiName;
    }
    
    public void setApiName(String apiName)
    {
        this.apiName = apiName;
    }
}
