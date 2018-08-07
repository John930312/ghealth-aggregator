package com.todaysoft.ghealth.config;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan("com.todaysoft.ghealth")
@EnableAspectJAutoProxy
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;

    @Autowired
    private Environment environment;
    
    @Bean(name = "characterEncodingFilter")
    public CharacterEncodingFilter getCharacterEncodingFilter()
    {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("utf-8");
        return filter;
    }
    
    @Bean(name = "messageSource")
    public ReloadableResourceBundleMessageSource getMessageSource()
    {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasenames("/WEB-INF/messages/errors");
        return messageSource;
    }
    
    @Override
    public void setApplicationContext(ApplicationContext context)
        throws BeansException
    {
        RootContext.context = context;
    }
    
    public static <T> T getBean(Class<T> requiredType)
    {
        return context.getBean(requiredType);
    }

    @Bean(name = "aliyunOSSConfig")
    public AliyunOSSConfig getAliyunOSSConfig()
    {
        AliyunOSSConfig config = new AliyunOSSConfig();
        config.setAccessKeyId(environment.getProperty("aliyun.oss.access.key", ""));
        config.setAccessKeySecret(environment.getProperty("aliyun.oss.access.secret", ""));
        config.setEndpoint(environment.getProperty("aliyun.oss.endpoint", ""));
        config.setBucketName(environment.getProperty("aliyun.oss.bucket.name", ""));
        config.setDirectoryName(environment.getProperty("aliyun.oss.directory.name", ""));
        config.setCallbackUrl(environment.getProperty("aliyun.oss.callbackUrl", ""));
        return config;
    }
}
