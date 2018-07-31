package com.todaysoft.ghealth.mvc;

import java.io.UnsupportedEncodingException;
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

import com.todaysoft.ghealth.base.response.model.GeneDetails;
import com.todaysoft.ghealth.model.gene.Gene;
import com.todaysoft.ghealth.model.gene.GeneForm;
import com.todaysoft.ghealth.model.gene.GeneSearcher;
import com.todaysoft.ghealth.service.IGeneService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/gene")
public class GeneAction extends BaseAction
{
    @Autowired
    private IGeneService service;
    
    @RequestMapping("/list.jsp")
    public String list(GeneSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Gene> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "gene/gene_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "gene/gene_create";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(GeneForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/gene/list.jsp");
    }
    
    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model)
    {
        GeneDetails data = service.get(id);
        model.addAttribute("data", data);
        
        return "gene/gene_details";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap map, HttpSession session)
    {
        
        GeneDetails data = service.get(id);
        map.addAttribute("data", data);
        return "gene/gene_modify";
    }
    
    @RequestMapping(value = "modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(GeneForm data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/gene/list.jsp");
    }
    
    @RequestMapping(value = "delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/gene/list.jsp");
    }
    
    @RequestMapping("json_list.do")
    @ResponseBody
    public List<Gene> jsonList(GeneSearcher searcher)
    {
        Pagination<Gene> pagination = service.searcher(searcher, 1, 10);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        return pagination.getRecords();
    }

    @RequestMapping(value = "/name_unique.do")
    @ResponseBody
    public boolean isNameUnique(String id, String name, ModelMap model) throws UnsupportedEncodingException {
        name = new String(name.getBytes("ISO-8859-1"), "UTF-8");
        return service.isNameUnique(id, name);
    }

    @RequestMapping(value = "/symbol_unique.do")
    @ResponseBody
    public boolean isSymbolUnique(String id, String symbol, ModelMap model) throws UnsupportedEncodingException {
        symbol = new String(symbol.getBytes("ISO-8859-1"), "UTF-8");
        return service.isSymbolUnique(id, symbol);
    }
}
