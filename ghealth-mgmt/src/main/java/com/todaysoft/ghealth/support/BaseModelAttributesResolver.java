package com.todaysoft.ghealth.support;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;

@Component
public class BaseModelAttributesResolver
{
    public void resolve(HttpServletRequest request, ModelMap model)
    {
        String base = request.getContextPath();
        model.addAttribute("base", base);
        model.addAttribute("plugins", base + "/static/plugins");
        model.addAttribute("system_js", base + "/static/system/js");
        model.addAttribute("system_css", base + "/static/system/css");
        model.addAttribute("system_images", base + "/static/system/images");
        
        // 当前登录账号
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        
        if (null != authentication)
        {
            Object principal = authentication.getPrincipal();
            
            if (principal instanceof AccountDetails)
            {
                AccountDetails account = (AccountDetails)principal;
                String name = StringUtils.isEmpty(account.getName()) ? account.getUsername() : account.getName();
                model.addAttribute("account_name", name);
            }
        }
    }
}
