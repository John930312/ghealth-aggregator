package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.model.drug.Drug;
import com.todaysoft.ghealth.model.drug.DrugSearcher;
import com.todaysoft.ghealth.service.IDrugService;
import com.todaysoft.ghealth.service.IGeneService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/drug")
public class DrugAction extends BaseAction {
    @Autowired
    private IDrugService service;

    @Autowired
    private IGeneService geneService;

    @RequestMapping(value = "/list.jsp")
    public String pagiantion(DrugSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Drug> pagination = service.pagination(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "drug/drug_list";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        return "drug/drug_form";
    }

    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(Drug data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session, "/drug/list.jsp");
    }

    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap map, HttpSession session)
    {

        Drug data = service.get(id);
        List<com.todaysoft.ghealth.base.response.model.GeneDetails> list = new ArrayList<>();
        for (String geneId :data.getGeneId().split(",")){
           list.add(geneService.get(geneId));
        }
        map.addAttribute("geneList",JsonUtils.toJson(list).toString());

        map.addAttribute("data", data);
        return "drug/drug_form";
    }

    @RequestMapping(value = "modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Drug data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, "/drug/list.jsp");
    }

    @RequestMapping(value = "/display.jsp", method = RequestMethod.GET)
    public String display(String id, ModelMap model)
    {
        Drug data = service.get(id);
        model.addAttribute("data", data);
        return "drug/drug_details";
    }

    @RequestMapping(value = "delete.jsp", method = RequestMethod.GET)
    public String delete(String id, ModelMap model, HttpSession session)
    {
        service.delete(id);
        return redirectList(model, session, "/drug/list.jsp");
    }

    @RequestMapping("/ingredientCn_unique.do")
    @ResponseBody
    public boolean isIngredientCnUnique(String id, String ingredientCn, ModelMap model)
    {
        return service.isIngredientCnUnique(id, ingredientCn);
    }
}
