package com.todaysoft.ghealth.config;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.filter.CharacterEncodingFilter;

@Configuration
@ComponentScan("com.todaysoft.ghealth")
@EnableAspectJAutoProxy
public class RootContext implements ApplicationContextAware
{
    private static ApplicationContext context;
    
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
}
