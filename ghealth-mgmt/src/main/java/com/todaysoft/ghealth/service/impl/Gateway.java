package com.todaysoft.ghealth.service.impl;

import java.io.IOException;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.ghealth.base.request.SignatureRequest;
import com.todaysoft.ghealth.base.request.SignatureTokenRequest;
import com.todaysoft.ghealth.base.response.ErrorResponse;
import com.todaysoft.ghealth.support.AccountDetails;
import com.todaysoft.ghealth.support.ServiceException;

@Service
public class Gateway
{
    private static Logger log = LoggerFactory.getLogger(Gateway.class);
    
    private static final String ACCESS_KEY = "DZuXmelufCfeuULbJIVlRNGulzbAbelt";
    
    private static final String SECRET_KEY = "oWmKwqfSkqOgIrllFSOqIbwclcFxYssZ";
    
    @Autowired
    private GatewayConfig config;
    
    private String context;
    
    private RestTemplate template = new RestTemplate();
    
    public void request(String uri, SignatureRequest request)
    {
        try
        {
            if (request instanceof SignatureTokenRequest)
            {

                AccountDetails account = (AccountDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                ((SignatureTokenRequest)request).setToken(account.getToken());
            }

            request.sign(ACCESS_KEY, SECRET_KEY);
            template.postForLocation(getUrl(uri), request);
        }
        catch (ResourceAccessException e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.API_UNREACHABLE);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
        }
    }
    
    public <T> T request(String uri, SignatureRequest request, Class<T> responseType)
    {
        try
        {
            if (request instanceof SignatureTokenRequest)
            {
                AccountDetails account = (AccountDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                ((SignatureTokenRequest)request).setToken(account.getToken());
            }
            
            request.sign(ACCESS_KEY, SECRET_KEY);
            
            return template.postForObject(getUrl(uri), request, responseType);
        }
        catch (ResourceAccessException e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.API_UNREACHABLE);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
        }
    }
    
    public <T> T request(String uri, SignatureRequest request, ParameterizedTypeReference<T> responseType)
    {
        try
        {
            if (request instanceof SignatureTokenRequest)
            {
                AccountDetails account = (AccountDetails)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                ((SignatureTokenRequest)request).setToken(account.getToken());
            }

            request.sign(ACCESS_KEY, SECRET_KEY);
            ResponseEntity<T> rsp = template.exchange(getUrl(uri), HttpMethod.POST, new HttpEntity<Object>(request), responseType);
            return rsp.getBody();
        }
        catch (ResourceAccessException e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.API_UNREACHABLE);
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
        }
        
    }
    
    protected String getUrl(String uri)
    {
        return this.context + uri;
    }
    
    @PostConstruct
    protected void init()
    {
        String name = StringUtils.isEmpty(config.getApiName()) ? "" : "/" + config.getApiName();
        this.context = String.format("http://%1$s:%2$s%3$s", config.getApiHost(), config.getApiPort(), name);
        template.setErrorHandler(new GatewayResponseErrorHandler());
    }
    
    private class GatewayResponseErrorHandler extends DefaultResponseErrorHandler
    {
        @Override
        public void handleError(ClientHttpResponse response)
            throws IOException
        {
            try
            {
                HttpStatus status = response.getStatusCode();
                
                switch (status.series())
                {
                    case CLIENT_ERROR:
                        throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
                    case SERVER_ERROR:
                        ErrorResponse rsp = new HttpMessageConverterExtractor<ErrorResponse>(ErrorResponse.class, template.getMessageConverters()).extractData(response);
                        ServiceException e = new ServiceException(rsp.getErrorCode());
                        throw e;
                    default:
                        throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
                }
            }
            catch (ServiceException e)
            {
                throw e;
            }
            catch (ResourceAccessException e)
            {
                log.error(e.getMessage(), e);
                throw new ServiceException(ErrorCode.API_UNREACHABLE);
            }
            catch (Exception e)
            {
                log.error(e.getMessage(), e);
                throw new ServiceException(ErrorCode.UNDEFINED_ERROR);
            }
        }
    }
}
