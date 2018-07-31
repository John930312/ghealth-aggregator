package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todaysoft.ghealth.base.response.model.Config;
import com.todaysoft.ghealth.model.config.ConfigSearcher;
import com.todaysoft.ghealth.service.IConfigService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/config")
public class ConfigAction extends BaseAction
{
    @Autowired
    private IConfigService configService;
    
    @RequestMapping("list.jsp")
    public String pagination(ConfigSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Config> pagination = configService.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "config/config_list";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(Config data, ModelMap model)
    {
        model.addAttribute("data", configService.get(data.getId()));
        return "/config/config_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Config data, ModelMap model, HttpSession session)
    {
        configService.modify(data);
        return redirectList(model, session, "/config/list.jsp");
    }

}
