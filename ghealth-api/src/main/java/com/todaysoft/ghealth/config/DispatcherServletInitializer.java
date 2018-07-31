package com.todaysoft.ghealth.config;

import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class DispatcherServletInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
    @Override
    protected Class<?>[] getRootConfigClasses()
    {
        return new Class[] {RootContext.class};
    }
    
    @Override
    protected Class<?>[] getServletConfigClasses()
    {
        return new Class[] {DispatcherServletContext.class};
    }
    
    @Override
    protected String[] getServletMappings()
    {
        return new String[] {"/*"};
    }
}