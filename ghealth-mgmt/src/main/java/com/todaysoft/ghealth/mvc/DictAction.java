package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.model.dict.Dict;
import com.todaysoft.ghealth.model.dict.DictSearcher;
import com.todaysoft.ghealth.service.IDictService;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/dict")
public class DictAction extends BaseAction
{
    @Autowired
    private IDictService service;

    @RequestMapping("/list.jsp")
    public String list(DictSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session) {
        Pagination<Dict> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "dict/dict_list";
    }

    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        Dict data = service.get(id);
        model.addAttribute("data", data);
        return "dict/dict_modify";
    }
    @RequestMapping(value = "modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Dict data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/dict/list.jsp");
    }

    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model)
    {
        Dict data = service.get(id);
        model.addAttribute("data", data);
        return "dict/dict_details";
    }


    @RequestMapping(value = "/change.jsp", method = RequestMethod.GET)
    @FormInputView
    public String change(String id, ModelMap model)
    {
        Dict data = service.get(id);
        model.addAttribute("data", data);
        return "dict/dict_change";
    }

    @RequestMapping(value = "change.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String change(Dict data, ModelMap model, HttpSession session)
    {
        service.change(data);
        return redirectList(model, session, "/dict/list.jsp");
    }

}
