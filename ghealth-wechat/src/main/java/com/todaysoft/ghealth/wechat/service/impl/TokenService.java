package com.todaysoft.ghealth.wechat.service.impl;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.wechat.dto.WechatAccountDTO;
import com.todaysoft.ghealth.wechat.dto.WechatTokenDTO;
import com.todaysoft.ghealth.wechat.model.MobileUser;
import com.todaysoft.ghealth.wechat.model.Token;
import com.todaysoft.ghealth.wechat.service.ITokenService;
import com.todaysoft.ghealth.wechat.service.core.AccountContextHolder;
import com.todaysoft.ghealth.wechat.service.core.Gateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TokenService implements ITokenService
{
    private static Logger log = LoggerFactory.getLogger(TokenService.class);
    
    @Autowired
    private Gateway gateway;
    
    @Autowired
    private AccountContextHolder accountContextHolder;
    
    @Override
    public Token getToken(String openid)
    {
        if (log.isDebugEnabled())
        {
            log.debug("Start to get token by openid {}.", openid);
        }
        
        DataResponse<WechatTokenDTO> response = gateway.get("/wechat/tokens/{openid}", new ParameterizedTypeReference<DataResponse<WechatTokenDTO>>()
        {
        }, openid);
        
        if (log.isDebugEnabled())
        {
            log.debug("Response status {}, message {}, data {}.", response.getStatus(), response.getMessage(), response.getData());
        }
        
        if (Objects.isNull(response.getData()))
        {
            return null;
        }
        
        Token token = new Token();
        BeanUtils.copyProperties(response.getData(), token);
        return token;
    }
    
    @Override
    public Token active(MobileUser data)
    {
        WechatAccountDTO dto = new WechatAccountDTO();
        dto.setPhone(data.getPhone());
        dto.setOpenid(accountContextHolder.getAccount().getOpenid());
        
        DataResponse<WechatTokenDTO> response = gateway.post("/wechat/tokens/active", dto, new ParameterizedTypeReference<DataResponse<WechatTokenDTO>>()
        {
        });
        
        Token token = new Token();
        BeanUtils.copyProperties(response.getData(), token);
        return token;
    }
    
    @Override
    public void freeze(String phone)
    {
        gateway.post("/wechat/tokens/freeze/{phone}", null, phone);
    }
    
    @Override
    public void unbind(String phone)
    {
        gateway.post("/wechat/tokens/unbind/{phone}", null, phone);
    }
    
    @Override
    public boolean existPhone(String phone)
    {
        DataResponse<Boolean> response = gateway.get("/wechat/tokens/existPhone/{phone}", new ParameterizedTypeReference<DataResponse<Boolean>>()
        {
        }, phone);
        return response.getData();
    }
}
