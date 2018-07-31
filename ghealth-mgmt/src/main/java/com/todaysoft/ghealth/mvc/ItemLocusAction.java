package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.todaysoft.ghealth.model.itemLocus.ItemLocusSearcher;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocus;
import com.todaysoft.ghealth.model.itemLocus.TestingItemLocusForm;
import com.todaysoft.ghealth.service.IItemLocusService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/item-locus")
public class ItemLocusAction extends BaseAction
{
    @Autowired
    private IItemLocusService itemLocusService;
    
    @RequestMapping(value = "/list.jsp")
    public String pagiantion(ItemLocusSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<TestingItemLocus> pagination = itemLocusService.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "itemLocus/itemLocus_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "itemLocus/itemLocus_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(TestingItemLocusForm data, ModelMap model, HttpSession session)
    {
        itemLocusService.create(data);
        return redirectList(model, session, "/item-locus/list.jsp");
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        TestingItemLocus itemLocus = itemLocusService.get(id);
        model.addAttribute("data", itemLocus);
        return "itemLocus/itemLocus_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(TestingItemLocusForm data, ModelMap model, HttpSession session)
    {
        itemLocusService.modify(data);
        return redirectList(model, session, "/item-locus/list.jsp");
    }
    
    @RequestMapping(value = "/delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        itemLocusService.delete(id);
        return redirectList(model, session, "/item-locus/list.jsp");
    }
}
