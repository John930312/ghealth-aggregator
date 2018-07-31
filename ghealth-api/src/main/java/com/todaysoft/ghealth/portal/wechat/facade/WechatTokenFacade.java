package com.todaysoft.ghealth.portal.wechat.facade;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.mybatis.model.AccountToken;
import com.todaysoft.ghealth.service.IWechatAccountService;
import com.todaysoft.ghealth.service.IWechatTokenService;
import com.todaysoft.ghealth.wechat.dto.WechatAccountDTO;
import com.todaysoft.ghealth.wechat.dto.WechatTokenDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class WechatTokenFacade
{
    @Autowired
    private IWechatTokenService service;
    
    @Autowired
    private IWechatAccountService accountService;
    
    public DataResponse<WechatTokenDTO> getToken(String openid)
    {
        AccountToken token = service.getTokenByOpenid(openid);
        
        if (Objects.isNull(token))
        {
            return new DataResponse<>(null);
        }
        
        accountService.modifyAccountToken(openid);
        
        WechatTokenDTO data = new WechatTokenDTO();
        data.setToken(token.getToken());
        return new DataResponse<>(data);
    }
    
    public DataResponse<WechatTokenDTO> active(WechatAccountDTO data)
    {
        return new DataResponse<>(accountService.createAccount(data));
    }

    public void freeze(String phone)
    {
        service.setAccountDataDisabled(phone);
    }

    public void unbind(String phone)
    {
        service.unbind(phone);
    }

    public DataResponse<Boolean> existPhone(String phone)
    {
        return new DataResponse<Boolean>(service.existPhone(phone));
    }
}
