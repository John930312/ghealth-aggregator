package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.ghealth.model.product.AgencyProduct;
import com.todaysoft.ghealth.model.product.AgencyProductDetails;
import com.todaysoft.ghealth.model.product.ProductSearcher;
import com.todaysoft.ghealth.service.IAgencyProductService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/product")
public class AgencyProductAction extends BaseAction
{
    @Autowired
    private IAgencyProductService service;
    
    @RequestMapping("/list.jsp")
    public String list(ProductSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<AgencyProduct> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "product/product_list";
    }
    
    @RequestMapping("/display.jsp")
    public String display(String id, ModelMap model)
    {
        AgencyProductDetails details = service.getAgencyProductDetails(id);
        model.addAttribute("details", details);
        return "product/product_details";
    }
    
    @RequestMapping("/json_list.do")
    @ResponseBody
    public List<AgencyProduct> jsonList(ProductSearcher searcher)
    {
        Pagination<AgencyProduct> pagination = service.searcher(searcher, 1, 10);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        return pagination.getRecords();
    }
    
    @RequestMapping(value = "/getSelectList.do")
    @ResponseBody
    public Map<String, Object> getSelectList(ProductSearcher searcher)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<AgencyProduct> pagination = service.searcher(searcher, 1, 100);
        Predicate<AgencyProduct> filterByProductId = (x) -> x.getProductId().equals(searcher.getProductId());
        List<AgencyProduct> datas = pagination.getRecords().stream().filter(filterByProductId.negate()).collect(Collectors.toList());
        map.put("value", datas);
        return map;
    }
}
