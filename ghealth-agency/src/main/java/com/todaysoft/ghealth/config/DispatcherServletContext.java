package com.todaysoft.ghealth.config;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.annotation.Resource;
import javax.servlet.ServletContext;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todaysoft.ghealth.service.freemarkerModel.FindDictByCategoryAndValue;
import com.todaysoft.ghealth.service.freemarkerModel.FindDictsByCategory;
import com.todaysoft.ghealth.service.freemarkerModel.SecurityResourceDirectiveModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.http.CacheControl;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.config.annotation.*;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer;
import org.springframework.web.servlet.view.freemarker.FreeMarkerViewResolver;

import com.todaysoft.ghealth.support.ExceptionResolver;

@EnableWebMvc
@ComponentScan(basePackages = "com.todaysoft.ghealth.mvc")
@EnableAspectJAutoProxy
public class DispatcherServletContext extends WebMvcConfigurerAdapter
{
    @Autowired
    private ExceptionResolver exceptionResolver;

    @Autowired
    private FindDictsByCategory findDictsByCategory;

    @Autowired
    private FindDictByCategoryAndValue findDictByCategoryAndValue;

    @Autowired
    private SecurityResourceDirectiveModel securityResourceDirectiveModel;

    @Resource(name = "formRepeatSubmitInterceptor")
    private HandlerInterceptorAdapter formRepeatSubmitInterceptor;
    
    @Override
    public void configureHandlerExceptionResolvers(List<HandlerExceptionResolver> exceptionResolvers)
    {
        exceptionResolvers.add(exceptionResolver);
    }


    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters)
    {
        ObjectMapper mapper = new ObjectMapper();
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.configure(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT, true);
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter(mapper);
        converters.add(converter);
    }
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry)
    {
        registry.addResourceHandler("/static/**").addResourceLocations("/static/").setCacheControl(CacheControl.empty());
    }
    
    @Override
    public void configureViewResolvers(ViewResolverRegistry registry)
    {
        FreeMarkerViewResolver resolver = new FreeMarkerViewResolver();
        resolver.setPrefix("/WEB-INF/views/");
        resolver.setSuffix(".html");
        resolver.setContentType("text/html;charset=UTF-8");
        registry.viewResolver(resolver);
    }
    
    @Override
    public void addFormatters(FormatterRegistry registry)
    {
        registry.addFormatter(new DateFormatter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry)
    {
        registry.addInterceptor(formRepeatSubmitInterceptor);
    }

    @Bean
    protected FreeMarkerConfigurer getFreeMarkerConfigurer()
    {
        Properties properties = new Properties();
        properties.setProperty("template_update_delay", "0");
        properties.setProperty("defaultEncoding", "UTF-8");
        properties.setProperty("url_escaping_charset", "UTF-8");
        properties.setProperty("locale", "zh_CN");
        properties.setProperty("boolean_format", "true,false");
        properties.setProperty("datetime_format", "yyyy-MM-dd HH:mm:ss");
        properties.setProperty("date_format", "yyyy-MM-dd");
        properties.setProperty("time_format", "HH:mm:ss");
        properties.setProperty("number_format", "0.##");
        properties.setProperty("whitespace_stripping", "true");
        
        Map<String, Object> variables = new HashMap<String, Object>();
        
        FreeMarkerConfigurer configurer = new FreeMarkerConfigurer();
        variables.put("dict_category",findDictsByCategory);
        variables.put("dict_category_value",findDictByCategoryAndValue);
        variables.put("security_resource",securityResourceDirectiveModel);
        configurer.setTemplateLoaderPath("");
        configurer.setFreemarkerSettings(properties);
        configurer.setFreemarkerVariables(variables);
        return configurer;
    }
    
    @Bean(name = "multipartResolver")
    public CommonsMultipartResolver getCommonsMultipartResolver(ServletContext servletContext)
    {
        CommonsMultipartResolver resolver = new CommonsMultipartResolver(servletContext);
        resolver.setMaxUploadSize(104857600);
        resolver.setMaxInMemorySize(104857600);
        return resolver;
    }
}
