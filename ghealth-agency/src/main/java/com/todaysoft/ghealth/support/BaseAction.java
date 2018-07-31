package com.todaysoft.ghealth.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;

public abstract class BaseAction
{
    @Autowired
    private BaseModelAttributesResolver baseModelAttributesResolver;
    
    @ModelAttribute
    protected void attributes(HttpServletRequest request, ModelMap model)
    {
        baseModelAttributesResolver.resolve(request, model);
    }

    protected String redirectList(ModelMap model, HttpSession session,String url)
    {
        model.clear();
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:" + url;
    }
}
