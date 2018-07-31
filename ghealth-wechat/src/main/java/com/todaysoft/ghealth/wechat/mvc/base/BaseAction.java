package com.todaysoft.ghealth.wechat.mvc.base;


import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public abstract class BaseAction
{
    
    public static final int DEFAULTPAGESIZE = 10;
    
    @ModelAttribute
    protected void attributes(HttpServletRequest request, ModelMap model)
    {
        String base = request.getContextPath();
        model.addAttribute("base", base);
        model.addAttribute("system_js", base + "/static/system/js");
        model.addAttribute("system_css", base + "/static/system/css");
        model.addAttribute("system_images", base + "/static/system/images");
        model.addAttribute("plugins", base + "/static/plugins");
        
    }
    

    protected String redirectList(ModelMap model, HttpSession session, String reUrl)
    {
        model.clear();
        model.addAttribute("pageNo", session.getAttribute("s-pageNo"));
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return reUrl;
    }
    

}
