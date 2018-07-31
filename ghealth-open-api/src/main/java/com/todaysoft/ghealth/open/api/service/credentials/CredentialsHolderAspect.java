package com.todaysoft.ghealth.open.api.service.credentials;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.todaysoft.ghealth.open.api.mybatis.model.Credentials;

@Aspect
@Component
public class CredentialsHolderAspect
{
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut()
    {
    }
    
    @Before("pointcut()")
    public void before(JoinPoint jp)
        throws Throwable
    {
        Object[] args = jp.getArgs();
        
        if (null == args)
        {
            return;
        }
        
        for (Object arg : args)
        {
            if (arg instanceof CredentialsHolder)
            {
                CredentialsHolder holder = (CredentialsHolder)arg;
                HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.currentRequestAttributes()).getRequest();
                holder.setCredentials((Credentials)request.getAttribute("CREDENTIALS"));
            }
        }
    }
}
