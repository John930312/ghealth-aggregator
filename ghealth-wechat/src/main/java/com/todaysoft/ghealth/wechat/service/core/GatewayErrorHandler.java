package com.todaysoft.ghealth.wechat.service.core;

import com.todaysoft.ghealth.base.response.ErrorResponse;
import com.todaysoft.ghealth.wechat.service.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.HttpMessageConverterExtractor;
import org.springframework.web.client.ResourceAccessException;

import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Component
public class GatewayErrorHandler extends DefaultResponseErrorHandler
{
    private static Logger log = LoggerFactory.getLogger(GatewayErrorHandler.class);
    
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
                    throw GatewayAccessException.undefined(MessageFormat.format("Http status code {0}.", status.value()));
                case SERVER_ERROR:
                    handleBusinessError(response);
                default:
                    throw GatewayAccessException.undefined(MessageFormat.format("Http status code {0}.", status.value()));
            }
        }
        catch (ResourceAccessException e)
        {
            log.error(e.getMessage(), e);
            throw GatewayAccessException.unreachable(e.getMessage());
        }
        catch (ServiceException e)
        {
            throw e;
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
            throw GatewayAccessException.undefined(e.getMessage());
        }
    }
    
    private void handleBusinessError(ClientHttpResponse response)
        throws IOException
    {
        List<HttpMessageConverter<?>> converters = new ArrayList<HttpMessageConverter<?>>();
        converters.add(new MappingJackson2HttpMessageConverter());
        ErrorResponse rsp = new HttpMessageConverterExtractor<ErrorResponse>(ErrorResponse.class, converters).extractData(response);

        throw new ServiceException(rsp.getErrorCode(), rsp.getErrorMessage());
    }
}