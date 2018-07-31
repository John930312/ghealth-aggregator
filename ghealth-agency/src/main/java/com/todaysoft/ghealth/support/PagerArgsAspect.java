package com.todaysoft.ghealth.support;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Aspect
@Component
public class PagerArgsAspect
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
        String requestMappingKey = getRequestMappingKey(jp);
        
        if (null != args)
        {
            for (Object arg : args)
            {
                if (arg instanceof PagerArgs)
                {
                    PagerArgs pagerArgs = (PagerArgs)arg;
                    
                    RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
                    HttpServletRequest request = ((ServletRequestAttributes)attributes).getRequest();
                    pagerArgs.setPageNo(getPageNo(request, null == requestMappingKey ? "" : requestMappingKey));
                    pagerArgs.setPageSize(getPageSize(request, null == requestMappingKey ? "" : requestMappingKey));
                }
            }
        }
    }
    
    @After("pointcut()")
    public void after(JoinPoint jp)
    {
        Object[] args = jp.getArgs();
        
        PagerArgs pagerArgs = null;
        
        if (null != args)
        {
            for (Object arg : args)
            {
                if (arg instanceof PagerArgs)
                {
                    pagerArgs = (PagerArgs)arg;
                }
            }
        }
        
        if (null != pagerArgs)
        {
            String requestMappingKey = getRequestMappingKey(jp);
            
            if (!StringUtils.isEmpty(requestMappingKey))
            {
                RequestAttributes attributes = RequestContextHolder.currentRequestAttributes();
                HttpSession session = ((ServletRequestAttributes)attributes).getRequest().getSession();
                session.setAttribute(requestMappingKey + "-pageNo", pagerArgs.getPageNo());
                session.setAttribute("s-pageSize", pagerArgs.getPageSize());
            }
        }
    }
    
    private String getRequestMappingKey(JoinPoint jp)
    {
        try
        {
            Class<?> clasz = jp.getTarget().getClass();
            MethodSignature signature = (MethodSignature)jp.getSignature();
            Method method = clasz.getMethod(signature.getName(), signature.getParameterTypes());
            
            StringBuffer buf = new StringBuffer();
            
            RequestMapping classRequestMapping = clasz.getAnnotation(RequestMapping.class);
            String[] value = classRequestMapping.value();
            
            if (null != value && value.length > 0)
            {
                buf.append(value[0]);
            }
            
            RequestMapping methodRequestMapping = method.getAnnotation(RequestMapping.class);
            
            value = methodRequestMapping.value();
            
            if (null != value && value.length > 0)
            {
                buf.append(value[0]);
            }
            
            return buf.toString();
        }
        catch (Exception e)
        {
            return null;
        }
    }
    
    private int getPageNo(HttpServletRequest request, String requestMappingKey)
    {
        // 请求内的优先级最高
        String no = request.getParameter("pageNo");
        
        if (!StringUtils.isEmpty(no))
        {
            return Integer.valueOf(no);
        }
        
        Integer pageNo = (Integer)request.getSession().getAttribute(requestMappingKey + "-pageNo");
        
        if (null != pageNo)
        {
            return pageNo;
        }
        
        return 1;
    }
    
    private int getPageSize(HttpServletRequest request, String requestMappingKey)
    {
        // 请求内的优先级最高
        String size = request.getParameter("pageSize");
        
        if (!StringUtils.isEmpty(size))
        {
            return Integer.valueOf(size);
        }
        
        Integer pageSize = (Integer)request.getSession().getAttribute("s-pageSize");
        
        if (null != pageSize)
        {
            return pageSize;
        }
        
        return 10;
    }
}
