package com.todaysoft.ghealth.portal;

import java.security.SignatureException;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.todaysoft.ghealth.base.request.SignatureRequest;
import com.todaysoft.ghealth.service.IAccessKeyService;

@Aspect
@Component
public class RequestSignAspect
{
    @Autowired
    private IAccessKeyService accessKeyService;
    
    @Pointcut("@annotation(org.springframework.web.bind.annotation.RequestMapping)")
    public void pointcut()
    {
    }
    
    @Before("pointcut()")
    public void before(JoinPoint jp)
        throws Throwable
    {
        Object[] args = jp.getArgs();
        
        if (null != args)
        {
            for (Object arg : args)
            {
                if (arg instanceof SignatureRequest)
                {
                    SignatureRequest request = (SignatureRequest)arg;
                    
                    if (null == request.getAccessKey() || null == request.getTimestamp() || null == request.getSignature())
                    {
                        throw new SignatureException();
                    }
                    
                    if (!accessKeyService.isValidKey(request.getAccessKey()))
                    {
                        throw new SignatureException();
                    }
                    
                    String secretKey = accessKeyService.getSecret(request.getAccessKey());
                    
                    if (!request.isValid(secretKey))
                    {
                        throw new SignatureException();
                    }
                }
            }
        }
    }
}
