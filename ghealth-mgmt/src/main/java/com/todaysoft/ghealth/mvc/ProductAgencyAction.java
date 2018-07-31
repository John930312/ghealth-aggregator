package com.todaysoft.ghealth.mvc;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;

import org.springframework.web.bind.annotation.RequestMapping;

import com.todaysoft.ghealth.model.product.Product;
import com.todaysoft.ghealth.model.product.ProductAgent;
import com.todaysoft.ghealth.model.product.TestingProductSearcher;

import com.todaysoft.ghealth.service.IProductAgentService;
import com.todaysoft.ghealth.service.ITestingProductService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/productAgent")
public class ProductAgencyAction extends BaseAction
{
    
    @Autowired
    private IProductAgentService poductService;
    
    @Autowired
    private ITestingProductService testingProductService;
    
    @RequestMapping("/list.jsp")
    public String pagination(TestingProductSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Product> pagination = testingProductService.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        return "product/product_list";
    }
    
    @RequestMapping("/distribute.jsp")
    public String distribute(String id, ModelMap model)
    {
        model.addAttribute("data", testingProductService.get(id));
        model.addAttribute("agencies", poductService.getAgencyList(id));
        model.addAttribute("datas", poductService.getProductAgentsByProductId(id));
        return "product/product_distribute";
    }
    
    @RequestMapping("/allocateAgent.do")
    public String allocateAgent(ProductAgent productAgent, ModelMap model, HttpSession session)
    {
        String a = productAgent.getTestingProductId();
        poductService.allocateAgent(productAgent);
        return redirectList(model, session, "/productAgent/distribute.jsp?id="+a);
    }
    
    @RequestMapping("details.jsp")
    public String view(String id, ModelMap model)
    {
        model.addAttribute("data", testingProductService.get(id));
        model.addAttribute("datas", poductService.getProductAgentsByProductId(id));
        return "product/product_details";
    }

    @RequestMapping(value = "modify.do", method = RequestMethod.POST)
    public String modify(ProductAgent data, ModelMap model, HttpSession session)
    {
        String a = data.getTestingProductId();
        data.setChangeType("3");
        poductService.modify(data);
        return redirectList(model, session, "/productAgent/distribute.jsp?id="+a);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public void delete(ProductAgent data, ModelMap model, HttpSession session)
    {

        poductService.delete(data);
    }

    @RequestMapping(value = "discountSetting.do", method = RequestMethod.POST)
    public String discountSetting(ProductAgent data, ModelMap model, HttpSession session)
    {
        String a = data.getTestingProductId();
        data.setDiscount(true);
        data.setChangeType("2");
        poductService.modify(data);
        return redirectList(model, session, "/productAgent/distribute.jsp?id="+a);
    }

    @RequestMapping(value = "/deleteAgency.do", method = RequestMethod.GET)
    public String deleteAgency(ProductAgent data, ModelMap model, HttpSession session)
    {
        String a = data.getTestingProductId();
        poductService.delete(data);
        return redirectList(model, session, "/productAgent/distribute.jsp?id="+a);
    }

    @RequestMapping(value = "/cancel.do", method = RequestMethod.GET)
    public String cancle(ProductAgent data, ModelMap model, HttpSession session)
    {
        String a = data.getTestingProductId();
        data.setDiscount(false);
        data.setChangeType("4");
        poductService.modify(data);
        return redirectList(model, session, "/productAgent/distribute.jsp?id="+a);
    }


}
