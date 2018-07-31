package com.todaysoft.ghealth.wechat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

import com.todaysoft.ghealth.wechat.service.core.GatewayConfig;
import com.todaysoft.ghealth.wechat.web.AccountFilter;

@SpringBootApplication(exclude = GsonAutoConfiguration.class)
@EnableConfigurationProperties(GatewayConfig.class)
public class Application
{
    @Autowired
    private AccountFilter accountFilter;
    
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
    
    @Bean
    public FilterRegistrationBean<AccountFilter> accountFilterRegistration()
    {
        FilterRegistrationBean<AccountFilter> registration = new FilterRegistrationBean<AccountFilter>();
        registration.setFilter(accountFilter);
        registration.addUrlPatterns("/", "*.jsp");
        registration.setName("AccountFilter");
        return registration;
    }
}
