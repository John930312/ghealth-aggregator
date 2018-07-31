package com.todaysoft.ghealth.wechat.service.wechat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.todaysoft.ghealth.wechat.service.wechat.dto.SNSTokenDTO;

@Component
public class OpenidProvider
{
    private static Logger log = LoggerFactory.getLogger(OpenidProvider.class);
    
    @Autowired
    private WechatService wechatService;
    
    public OpenidHolder getOpenid(HttpServletRequest request)
    {
        OpenidHolder holder = new OpenidHolder();

        HttpSession session = request.getSession();
        String openid = getOpenid(session);
        
        if (!StringUtils.isEmpty(openid))
        {
            holder.setOpenid(openid);
            return holder;
        }
        
        String code = request.getParameter("code");
        
        if (StringUtils.isEmpty(code))
        {
            if (log.isDebugEnabled())
            {
                String redirect = wechatService.getOauth2AuthorizeUrl(request);
                log.debug("Wechat code is empty, redirect to {}.", redirect);
            }
            
            String redirect = wechatService.getOauth2AuthorizeUrl(request);
            holder.setRedirect(true);
            holder.setRedirectUrl(redirect);
            return holder;
        }
        
        SNSTokenDTO token = wechatService.getSNSToken(code);
        
        if (null == token)
        {
            String redirect = wechatService.getOauth2AuthorizeUrl(request);
            holder.setRedirect(true);
            holder.setRedirectUrl(redirect);
        }
        
        openid = token.getOpenid();
        
        if (StringUtils.isEmpty(openid))
        {
            log.error("Can not get the openid, ignore the request.");
            throw new IllegalStateException();
        }
        
        session.setAttribute("ACCOUNT_OPENID", openid);
        holder.setOpenid(openid);
        return holder;
    }
    
    public String getOpenid(HttpSession session)
    {
        return (String)session.getAttribute("ACCOUNT_OPENID");
    }
}
