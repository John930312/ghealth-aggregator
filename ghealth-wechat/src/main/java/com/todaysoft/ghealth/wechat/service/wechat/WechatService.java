package com.todaysoft.ghealth.wechat.service.wechat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.util.Collections;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.todaysoft.ghealth.wechat.service.wechat.dto.SNSTokenDTO;

@Service
public class WechatService
{
    private static Logger log = LoggerFactory.getLogger(WechatService.class);
    
    private static final String APPID = "wxc21d870523a7b3a6";
    
    private static final String SECRET = "870a00d950cd76b3d772f97a0242f9aa";
    
    public String getOauth2AuthorizeUrl(HttpServletRequest request)
    {
        try
        {
            String url = request.getRequestURL() + (request.getQueryString() != null ? "?" + request.getQueryString() : "");
            String pattern = "https://open.weixin.qq.com/connect/oauth2/authorize?appid={0}&redirect_uri={1}&response_type=code&scope={2}&state={3}#wechat_redirect";
            return MessageFormat.format(pattern, APPID, URLEncoder.encode(url, "UTF-8"), "snsapi_base", "2");
        }
        catch (UnsupportedEncodingException e)
        {
            throw new IllegalStateException();
        }
    }
    
    public SNSTokenDTO getSNSToken(String code)
    {
        try
        {
            String url = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code&appid={key}&secret={security}&code={code}";
            RestTemplate template = new RestTemplate();
            MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
            converter.setSupportedMediaTypes(Collections.singletonList(MediaType.TEXT_PLAIN));
            template.setMessageConverters(Collections.singletonList(converter));
            SNSTokenDTO token = template.getForObject(url, SNSTokenDTO.class, APPID, SECRET, code);
            
            if (null == token)
            {
                log.error("Get sns token error, response is null.");
                return null;
            }
            
            if (!token.isValid())
            {
                log.error("Get sns token error, error code {}, error message {}", token.getErrorCode(), token.getErrorMessage());
                return null;
            }
            
            return token;
        }
        catch (Exception e)
        {
            log.error("Get sns token error.", e);
            return null;
        }
    }
}
