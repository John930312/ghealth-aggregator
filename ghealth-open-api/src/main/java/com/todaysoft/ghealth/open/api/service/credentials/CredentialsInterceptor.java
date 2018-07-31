package com.todaysoft.ghealth.open.api.service.credentials;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.todaysoft.ghealth.open.api.mybatis.model.Credentials;
import com.todaysoft.ghealth.open.api.service.ICredentialsService;

@Component
public class CredentialsInterceptor extends HandlerInterceptorAdapter
{
    private static Logger log = LoggerFactory.getLogger(CredentialsInterceptor.class);
    
    private static final String REQUEST_HEADER_TIMESTAMP = "X-Timestamp";
    
    private static final String REQUEST_HEADER_AUTHORIZATION = "X-Authorization";
    
    @Autowired
    private ICredentialsService credentialsService;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
        throws Exception
    {
        String timestamp = request.getHeader(REQUEST_HEADER_TIMESTAMP);
        
        if (StringUtils.isEmpty(timestamp))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Timestamp request header is empty, set the request forbidden.");
            }
            
            return forbidden(response, "Authorization Error.");
        }
        
        String authorization = request.getHeader(REQUEST_HEADER_AUTHORIZATION);
        
        if (StringUtils.isEmpty(authorization))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Authorization request header is empty, set the request forbidden.");
            }
            
            return forbidden(response, "Authorization Error.");
        }
        
        String[] array = authorization.split(":");
        
        if (array.length != 2)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Authorization header is illegal, set the request forbidden.");
            }
            
            return forbidden(response, "Authorization Error.");
        }
        
        String key = array[0];
        Credentials credentials = credentialsService.getCredentials(key);
        
        if (null == credentials)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Can not found credentials for key {}, set the request forbidden.", key);
            }
            
            return forbidden(response, "Authorization Error.");
        }
        
        if (!credentials.isEnabled())
        {
            if (log.isDebugEnabled())
            {
                log.debug("Credentials is disabled for key {}, set the request forbidden.", key);
            }
            
            return forbidden(response, "Authorization Error.");
        }
        
        String secret = credentials.getSecret();
        String digestSource = timestamp + secret;
        String sign = DigestUtils.md5DigestAsHex(digestSource.getBytes()).toUpperCase();
        
        if (!sign.equals(array[1]))
        {
            if (log.isDebugEnabled())
            {
                log.debug("Request sign invalid, set the request forbidden.", key);
            }
            
            return forbidden(response, "Signature Error.");
        }
        
        request.setAttribute("CREDENTIALS", credentials);
        return true;
    }
    
    private boolean forbidden(HttpServletResponse response, String message)
        throws IOException
    {
        response.sendError(HttpServletResponse.SC_FORBIDDEN, message);
        return false;
    }
}
