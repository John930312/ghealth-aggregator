package com.todaysoft.ghealth.wechat.web;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.todaysoft.ghealth.wechat.service.core.Account;
import com.todaysoft.ghealth.wechat.service.core.AccountProvider;
import com.todaysoft.ghealth.wechat.service.wechat.WechatService;

@Component
public class AccountFilter extends OncePerRequestFilter
{
    private static Logger log = LoggerFactory.getLogger(AccountFilter.class);

    @Autowired
    private WechatService wechatService;

    @Autowired
    private AccountProvider provider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException
    {
        Account account = (Account) request.getSession().getAttribute("WECHAT_ACCOUNT");

        if (null == account)
        {
            if (log.isDebugEnabled())
            {
                log.debug("Session account is not exists, try to provide by the request.");
            }

            account = provider.provide(request);

            if (null == account)
            {
                String redirect = wechatService.getOauth2AuthorizeUrl(request);
                response.sendRedirect(redirect);
            }
            else
            {
                request.getSession().setAttribute("WECHAT_ACCOUNT", account);

                if (log.isDebugEnabled())
                {
                    log.debug("Set account to session and finish the filter, account openid {}, name {}, token {}.", account.getOpenid(),
                            account.getName(), account.getToken());
                }

                filterChain.doFilter(request, response);
            }
        }
        else
        {
            if (log.isDebugEnabled())
            {
                log.debug("Session account is exists, finish the filter, account openid {}, name {}, token {}.", account.getOpenid(),
                        account.getName(), account.getToken());
            }

            filterChain.doFilter(request, response);
        }
    }
}
