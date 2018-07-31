package com.todaysoft.ghealth.mvc;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.base.response.model.AgencyProductDetails;
import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import com.todaysoft.ghealth.model.apa.AgencyProduct;
import com.todaysoft.ghealth.utils.JsonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.ghealth.model.item.TestingItem;
import com.todaysoft.ghealth.model.item.TestingItemSearcher;
import com.todaysoft.ghealth.model.product.Product;
import com.todaysoft.ghealth.model.product.ProductDetails;
import com.todaysoft.ghealth.model.product.TestingProductForm;
import com.todaysoft.ghealth.model.product.TestingProductSearcher;
import com.todaysoft.ghealth.service.ITestingItemService;
import com.todaysoft.ghealth.service.ITestingProductService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/product")
public class TestingProductAction extends BaseAction
{
    @Autowired
    private ITestingProductService service;
    
    @Autowired
    private ITestingItemService itemService;
    
    private String redirectUrl = "/product/list.jsp";
    
    @RequestMapping("/list.jsp")
    public String list(TestingProductSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Product> pagination = service.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "testingproduct/product_list";
    }
    
    @RequestMapping("/code_unique.do")
    @ResponseBody
    public boolean isCodeUnique(String id, String code, ModelMap model)
    {
        return service.isCodeUnique(id, code);
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(String id, ModelMap model)
    {
        List<TestingItem> items = itemService.list(new TestingItemSearcher());
        model.addAttribute("selectableItems", items);
        return "testingproduct/product_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(TestingProductForm request, ModelMap model, HttpSession session)
    {
        service.create(request);
        return redirectList(model, session, redirectUrl);
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        List<TestingItem> items = itemService.list(new TestingItemSearcher());
        ProductDetails details = service.get(id);
        List<TestingItem> productTestingItems = details.getTestingItems();
        
        if (!CollectionUtils.isEmpty(productTestingItems))
        {
            Set<String> keys = new HashSet<String>();
            
            for (TestingItem productTestingItem : productTestingItems)
            {
                keys.add(productTestingItem.getId());
            }
            
            List<TestingItem> selectedItems = new ArrayList<TestingItem>();
            List<TestingItem> selectableItems = new ArrayList<TestingItem>(items);
            
            for (TestingItem item : productTestingItems)
            {
                if (keys.contains(item.getId()))
                {
                    selectableItems.remove(item);
                    selectedItems.add(item);
                }
            }
            
            model.addAttribute("selectedItems", selectedItems);
            model.addAttribute("selectableItems", selectableItems);
        }
        else
        {
            model.addAttribute("selectableItems", items);
        }
        
        model.addAttribute("data", details);
        return "testingproduct/product_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(TestingProductForm request, ModelMap model, HttpSession session)
    {
        service.modify(request);
        return redirectList(model, session, redirectUrl);
    }
    
    @RequestMapping(value = "/delete.jsp")
    @ResponseBody
    public boolean modify(String id, ModelMap model, HttpSession session)
    {
        return service.delete(id);
    }
    
    @RequestMapping("/display.jsp")
    public String display(String id, ModelMap model)
    {
        ProductDetails details = service.get(id);
        model.addAttribute("data", details);
        return "testingproduct/product_details";
    }
    
    @RequestMapping(value = "/getSelectList.do")
    @ResponseBody
    public Map<String, Object> getSelectList(TestingProductSearcher searcher)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<Product> datas = service.search(searcher, 1, 100);
        map.put("value", datas.getRecords());
        return map;
    }
    
    @RequestMapping(value = "/getAgencyProducts.do")
    @ResponseBody
    public Map<String, Object> getAgencyProducts(TestingProductSearcher searcher)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<AgencyProduct> pagination = service.getAgencyProducts(searcher, 1, 10);
        Predicate<AgencyProduct> filterByProductId = (x) -> x.getProductId().equals(searcher.getProductId());
        List<AgencyProduct> datas = pagination.getRecords().stream().filter(filterByProductId.negate()).collect(Collectors.toList());
        map.put("value", datas);
        return map;
    }
    
    @RequestMapping("json_list.do")
    @ResponseBody
    public List<Product> jsonList(TestingProductSearcher searcher)
    {
        Pagination<Product> pagination = service.search(searcher, 1, 10);
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
    public boolean setIsEnabled(TestingProductForm request)
    {
        return service.setIsEnabled(request);
    }
    
    @ResponseBody
    @RequestMapping("/getProductDetails.do")
    public ProductDetails getProductDetails(String id)
    {
        return service.get(id);
    }
    
    @ResponseBody
    @RequestMapping("/getAgencyProductDetails.do")
    public AgencyProductDetails getAgencyProductDetails(String id)
    {
        return service.getAgencyProductDetails(id);
    }
}
