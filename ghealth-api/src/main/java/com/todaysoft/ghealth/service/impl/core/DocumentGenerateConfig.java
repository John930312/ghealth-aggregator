package com.todaysoft.ghealth.service.impl.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration
@PropertySource({"classpath:application-context.properties"})
public class DocumentGenerateConfig
{
    @Value("${document.generate.endpoint}")
    private String endpoint;
    
    @Value("${document.generate.access.key}")
    private String accessKeyId;
    
    @Value("${document.generate.access.secret}")
    private String accessKeySecret;
    
    @Value("${document.generate.app.key}")
    private String appid;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev()
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
    
    public String getEndpoint()
    {
        return endpoint;
    }
    
    public void setEndpoint(String endpoint)
    {
        this.endpoint = endpoint;
    }
    
    public String getAccessKeyId()
    {
        return accessKeyId;
    }
    
    public void setAccessKeyId(String accessKeyId)
    {
        this.accessKeyId = accessKeyId;
    }
    
    public String getAccessKeySecret()
    {
        return accessKeySecret;
    }
    
    public void setAccessKeySecret(String accessKeySecret)
    {
        this.accessKeySecret = accessKeySecret;
    }
    
    public String getAppid()
    {
        return appid;
    }
    
    public void setAppid(String appid)
    {
        this.appid = appid;
    }
}
