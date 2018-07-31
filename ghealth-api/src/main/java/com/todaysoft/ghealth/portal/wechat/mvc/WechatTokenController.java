package com.todaysoft.ghealth.portal.wechat.mvc;

import com.hsgene.restful.response.DataResponse;
import com.todaysoft.ghealth.portal.wechat.facade.WechatTokenFacade;
import com.todaysoft.ghealth.wechat.dto.WechatAccountDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.todaysoft.ghealth.wechat.dto.WechatTokenDTO;

@RestController
@RequestMapping("/wechat/tokens")
public class WechatTokenController
{
    @Autowired
    private WechatTokenFacade facade;
    
    @RequestMapping("/{openid}")
    public DataResponse<WechatTokenDTO> getToken(@PathVariable String openid)
    {
        return facade.getToken(openid);
    }
    
    @RequestMapping(value = "/active", method = RequestMethod.POST)
    public DataResponse<WechatTokenDTO> active(@RequestBody WechatAccountDTO data)
    {
        return facade.active(data);
    }

    @RequestMapping("/freeze/{phone}")
    public void freeze(@PathVariable String phone)
    {
        facade.freeze(phone);
    }

    @RequestMapping("/unbind/{phone}")
    public void unbind(@PathVariable String phone)
    {
        facade.unbind(phone);
    }

    @RequestMapping("/existPhone/{phone}")
    public DataResponse<Boolean> existPhone(@PathVariable String phone)
    {
       return facade.existPhone(phone);
    }
}
