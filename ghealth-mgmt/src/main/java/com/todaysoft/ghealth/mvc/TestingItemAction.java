package com.todaysoft.ghealth.mvc;

import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.todaysoft.ghealth.model.cancer.Cancer;
import com.todaysoft.ghealth.model.item.TestingItem;
import com.todaysoft.ghealth.model.item.TestingItemForm;
import com.todaysoft.ghealth.model.item.TestingItemSearcher;
import com.todaysoft.ghealth.service.ICancerService;
import com.todaysoft.ghealth.service.ITestingItemService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/item")
public class TestingItemAction extends BaseAction
{
    @Autowired
    private ITestingItemService service;
    
    @Autowired
    
    private ICancerService cancerService;
    
    private String redirectUrl = "/item/list.jsp";
    
    @RequestMapping("/list.jsp")
    public String list(TestingItemSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<TestingItem> pagination = service.pager(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "item/item_list";
    }
    
    @RequestMapping("/all.jsp")
    @ResponseBody
    public List<TestingItem> list(TestingItemSearcher searcher, ModelMap model, HttpSession session)
    {
        List<TestingItem> result = service.list(searcher);
        return result;
    }
    
    @RequestMapping("/code_unique.do")
    @ResponseBody
    public boolean isCodeUnique(String id, String code, ModelMap model)
    {
        return service.isCodeUnique(id, code);
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    public String create(ModelMap model)
    {
        return "item/item_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    public String create(TestingItemForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, redirectUrl);
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)

    public String modify(String id, ModelMap model, HttpSession session)
    {
        TestingItem data = service.get(id);
        model.addAttribute("data", data);
        if (StringUtils.isNotEmpty(data.getCategoryMapping()))
        {
            Cancer cancer = new Cancer();
            cancer.setId(data.getCategoryMapping());
            model.addAttribute("cancer", JSON.toJSON(cancerService.get(cancer.getId())).toString());
        }
        
        return "item/item_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    public String modify(TestingItemForm data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, redirectUrl);
    }
    
    @RequestMapping(value = "/delete.jsp")
    @ResponseBody
    public boolean delete(String id, ModelMap model, HttpSession session)
    {
       return service.delete(id);
    }
    
    @RequestMapping("/display.jsp")
    public String display(String id, ModelMap model)
    {
        TestingItem data = service.get(id);
        model.addAttribute("data", data);
        return "item/item_details";
    }
    
    @RequestMapping("/json_list.do")
    @ResponseBody
    public List<TestingItem> jsonList(TestingItemSearcher searcher)
    {
        Pagination<TestingItem> pagination = service.pager(searcher, 1, 10);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        return pagination.getRecords();
    }

    @RequestMapping("/reload.do")
    public String reload(ModelMap model, HttpSession session)
    {
        return redirectList(model, session, redirectUrl);
    }

    @RequestMapping("/setIsEnabled.do")
    @ResponseBody
    public Boolean setIsEnabled(TestingItemForm data)
    {
        return service.setIsEnabled(data);
    }
}
