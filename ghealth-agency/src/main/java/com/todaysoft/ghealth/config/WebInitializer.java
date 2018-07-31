package com.todaysoft.ghealth.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration.Dynamic;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import org.springframework.core.annotation.Order;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.filter.DelegatingFilterProxy;

@Order(1)
public class WebInitializer implements WebApplicationInitializer
{
    private static final String CHARACTER_ENCODING_FILTER_BEAN = "characterEncodingFilter";
    
    private static final String CHARACTER_ENCODING_FILTER_NAME = "CharacterEncodingFilter";
    
    private static final String[] CHARACTER_ENCODING_FILTER_PATTERNS = new String[] {"*.jsp", "*.do","/"};
    
    @Override
    public void onStartup(ServletContext servletContext)
        throws ServletException
    {
        registerCharacterEncodingFilter(servletContext);
    }
    
    private void registerCharacterEncodingFilter(ServletContext servletContext)
        throws ServletException
    {
        DelegatingFilterProxy filter = new DelegatingFilterProxy(CHARACTER_ENCODING_FILTER_BEAN);
        filter.setTargetFilterLifecycle(true);
        Dynamic registration = servletContext.addFilter(CHARACTER_ENCODING_FILTER_NAME, filter);
        registration.setAsyncSupported(true);
        EnumSet<DispatcherType> dispatcherTypes = EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD);
        registration.addMappingForUrlPatterns(dispatcherTypes, false, CHARACTER_ENCODING_FILTER_PATTERNS);
    }
}
