package com.todaysoft.ghealth.wechat.service.core;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import com.hsgene.restful.response.DataResponse;

@Component
public class Gateway extends RestTemplate
{
    private String endpoint;
    
    @Autowired
    private GatewayConfig config;
    
    @Autowired
    private GatewayErrorHandler errorHandler;
    
    public <T> DataResponse<T> get(String uri, ParameterizedTypeReference<DataResponse<T>> type, Object... uriVariables)
    {
        return this.get(uri, null, type, uriVariables);
    }
    
    public <T> DataResponse<T> get(String uri, HttpHeaders headers, ParameterizedTypeReference<DataResponse<T>> type, Object... uriVariables)
    {
        HttpEntity<Object> entity = new HttpEntity<Object>(null, headers);
        ResponseEntity<DataResponse<T>> exchange = this.exchange(endpoint + uri, HttpMethod.GET, entity, type, uriVariables);
        return exchange.getBody();
    }
    
    public <T> DataResponse<T> post(String uri, Object body, ParameterizedTypeReference<DataResponse<T>> type)
    {
        return this.post(uri, body, null, type);
    }
    
    public <T> DataResponse<T> post(String uri, Object body, HttpHeaders headers, ParameterizedTypeReference<DataResponse<T>> type)
    {
        HttpEntity<Object> entity = new HttpEntity<Object>(body, headers);
        ResponseEntity<DataResponse<T>> exchange = this.exchange(endpoint + uri, HttpMethod.POST, entity, type);
        return exchange.getBody();
    }
    
    public void post(String uri, Object body, Object... uriVariables)
    {
        this.postForLocation(endpoint + uri, body, uriVariables);
    }
    
    @PostConstruct
    protected void init()
    {
        String name = StringUtils.isEmpty(config.getPath()) ? "" : "/" + config.getPath();
        this.endpoint = String.format("http://%1$s:%2$s%3$s", config.getHost(), config.getPort(), name);
        this.setErrorHandler(errorHandler);
    }
}
