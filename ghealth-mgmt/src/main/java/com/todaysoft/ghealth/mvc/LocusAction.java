package com.todaysoft.ghealth.mvc;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.ghealth.model.locus.Locus;
import com.todaysoft.ghealth.model.locus.LocusSearcher;
import com.todaysoft.ghealth.service.ILocusService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/locus")
public class LocusAction extends BaseAction
{
    @Autowired
    private ILocusService locusService;
    
    @RequestMapping(value = "/list.jsp")
    public String pagiantion(LocusSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Locus> pagination = locusService.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "locus/locus_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "locus/locus_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(Locus data, ModelMap model, HttpSession session)
    {
        locusService.create(data);
        return redirectList(model, session, "/locus/list.jsp");
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model, HttpSession session)
    {
        Locus locus = locusService.get(id);
        model.addAttribute("data", locus);
        return "locus/locus_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Locus data, ModelMap model, HttpSession session)
    {
        locusService.modify(data);
        return redirectList(model, session, "/locus/list.jsp");
    }
    
    @RequestMapping(value = "/delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        locusService.delete(id);
        return redirectList(model, session, "/locus/list.jsp");
    }
    
    @RequestMapping(value = "/json_list.do", method = RequestMethod.GET)
    @ResponseBody
    public List<Locus> jsonList(LocusSearcher searcher, ModelMap model, HttpSession session)
    {
        Pagination<Locus> pagination = locusService.pagination(searcher, 1, 10);
        
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        
        return pagination.getRecords();
    }
    
    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model, HttpSession session)
    {
        Locus locus = locusService.get(id);
        model.addAttribute("data", locus);
        return "locus/locus_details";
    }
    
    @RequestMapping("/name_unique.do")
    @ResponseBody
    public boolean isNameUnique(String id, String name, ModelMap model)
    {
        return locusService.isNameUnique(id, name);
    }
}
