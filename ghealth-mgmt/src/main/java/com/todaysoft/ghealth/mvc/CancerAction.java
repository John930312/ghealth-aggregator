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

import com.todaysoft.ghealth.model.cancer.Cancer;
import com.todaysoft.ghealth.model.cancer.CancerForm;
import com.todaysoft.ghealth.model.cancer.CancerSearcher;
import com.todaysoft.ghealth.service.ICancerService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/cancer")
public class CancerAction extends BaseAction
{
    @Autowired
    private ICancerService service;
    
    @RequestMapping("/list.jsp")
    public String list(CancerSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Cancer> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "cancer/cancer_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "cancer/cancer_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(CancerForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/cancer/list.jsp");
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap map, HttpSession session)
    {
        
        Cancer data = service.get(id);
        map.addAttribute("data", data);
        return "cancer/cancer_form";
    }
    
    @RequestMapping(value = "modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Cancer data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/cancer/list.jsp");
    }
    
    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model)
    {
        Cancer data = service.get(id);
        model.addAttribute("data", data);
        return "cancer/cancer_details";
    }
    
    @RequestMapping(value = "delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/cancer/list.jsp");
    }
    
    @RequestMapping("json_list.do")
    @ResponseBody
    public List<Cancer> jsonList(CancerSearcher searcher)
    {
        Pagination<Cancer> pagination = service.searcher(searcher, 1, 10);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        return pagination.getRecords();
    }
    
    @RequestMapping(value = "/name_unique.do")
    @ResponseBody
    public boolean isNameUnique(String id, String name, ModelMap model)
    {
        return service.isNameUnique(id, name);
    }
}
