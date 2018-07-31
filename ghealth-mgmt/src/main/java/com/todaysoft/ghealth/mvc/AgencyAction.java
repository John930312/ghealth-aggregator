package com.todaysoft.ghealth.mvc;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.model.apa.AgencyProduct;
import com.todaysoft.ghealth.model.product.AgencyProductOldPrice;
import com.todaysoft.ghealth.model.product.Product;
import com.todaysoft.ghealth.model.product.ProductAgent;
import com.todaysoft.ghealth.service.IProductAgentService;
import com.todaysoft.ghealth.service.ITestingProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.model.agency.Agency;
import com.todaysoft.ghealth.model.agency.AgencyDetails;
import com.todaysoft.ghealth.model.agency.AgencyForm;
import com.todaysoft.ghealth.model.agency.AgencySearcher;
import com.todaysoft.ghealth.service.IAgencyService;
import com.todaysoft.ghealth.service.IAreaService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.ModelResolver;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;

@Controller
@RequestMapping("/agency")
public class AgencyAction extends BaseAction
{
    @Autowired
    private IAgencyService service;
    
    @Autowired
    private IAreaService areaService;

    @Autowired
    private ITestingProductService testingProductService;

    @Autowired
    private IProductAgentService productAgentService;

    @Autowired
    private IProductAgentService poductService;
    
    @RequestMapping(value = "/list.jsp")
    public String list(AgencySearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Agency> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "agency/agency_list";
    }
    
    @RequestMapping("/username_unique.do")
    @ResponseBody
    public boolean isUsernameUnique(String primaryUsername, ModelMap model)
    {
        return service.isUsernameUnique(primaryUsername);
    }
    
    @RequestMapping("/code_unique.do")
    @ResponseBody
    public boolean isCodeUnique(String id, String code, ModelMap model)
    {
        return service.isCodeUnique(id, code);
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        List<Area> list = areaService.findProvince();
        model.addAttribute("list", list);
        return "agency/agency_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(AgencyForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session,"/agency/list.jsp");
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        AgencyDetails details = service.get(id);
        model.addAttribute("data", details);
        Area provinceArea = new Area();
        if (!StringUtils.isEmpty(details.getProvince()))
        {
            provinceArea.setId(details.getProvince());
        }
        
        if (!StringUtils.isEmpty(details.getProvince()))
        {
            List<Area> province = areaService.findByParentId(details.getProvince());
            model.addAttribute("province", province);
        }
        Area cityArea = new Area();
        if (!StringUtils.isEmpty(details.getCity()))
        {
            cityArea.setId(details.getCity());
        }
        
        if (!StringUtils.isEmpty(details.getCity()))
        {
            List<Area> city = areaService.findByParentId(details.getCity());
            model.addAttribute("city", city);
        }
        List<Area> list = areaService.findProvince();
        model.addAttribute("list", list);
        return "agency/agency_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(AgencyForm data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session,"/agency/list.jsp");
    }
    
    @RequestMapping("/delete.jsp")
    @ResponseBody
    public Boolean delete(String id, ModelMap model, HttpSession session)
    {
        return service.delete(id);
    }
    
    @RequestMapping("/display.jsp")
    public String display(String id, ModelMap model)
    {
        AgencyDetails details = service.get(id);
        model.addAttribute("data", details);
        return "agency/agency_details";
    }

    
    @RequestMapping("/json_list.do")
    @ResponseBody
    public List<Agency> jsonList(AgencySearcher searcher)
    {
        Pagination<Agency> pagination = service.searcher(searcher, 1, 10);
        if (CollectionUtils.isEmpty(pagination.getRecords()))
        {
            return Collections.emptyList();
        }
        return pagination.getRecords();
    }
    
    @RequestMapping("/getAreas")
    @ResponseBody
    public List<Area> findByParentId(ModelMap model, String parentId)
    {
        List<Area> result = areaService.findByParentId(parentId);
        return result;
    }
    
    @RequestMapping("/reload.do")
    public String reload(ModelMap model, HttpSession session)
    {
        return redirectList(model, session,"/agency/list.jsp");
    }
    
    @RequestMapping("/recharge.jsp")
    public String recharge(AgencyForm data, ModelMap model, HttpSession session)
    {
        service.recharge(data);
        return redirectList(model, session,"/agency/list.jsp");
    }

    @RequestMapping(value = "/addProduct.do", method = RequestMethod.GET)
    public String addProduct(String id, ModelMap model)
    {


        AgencyDetails details = service.get(id);
        List<Product> products = testingProductService.getProduct();
        List<Product> datas = new ArrayList<>();
        List<String> sourceProductIds = new ArrayList<>();
        for(AgencyProduct agencyProduct :details.getAgentProducts() )
        {
            sourceProductIds.add(agencyProduct.getProductId());
        }
        for(Product product :products)
        {
            if(sourceProductIds.contains(product.getId()))
            {
                continue;
            }
            datas.add(product);
        }
        model.addAttribute("data", details);
        model.addAttribute("datas", datas);
        return "agency/agency_addProduct";
    }

    @RequestMapping(value = "/addPro.do", method = RequestMethod.POST)
    public String addPro(ProductAgent data, ModelMap model,HttpSession session){
        String a = data.getAgencyId();
        data.setAgencyPrice(data.getGuidingPrice());
        productAgentService.addPro(data);
        return redirectList(model, session,"/agency/addProduct.do?id="+a);
    }

    @RequestMapping(value = "/modifyPrice.do", method = RequestMethod.POST)
    public String modifyPrice(ProductAgent data, ModelMap model, HttpSession session, HttpServletRequest request)
    {

        String a = data.getAgencyId();
        data.setChangeType("1");
        poductService.modify(data);
        return redirectList(model, session,"/agency/addProduct.do?id="+a);
    }

    @RequestMapping(value = "/deleteProduct.do", method = RequestMethod.GET)
    public String deleteProduct(ProductAgent data, ModelMap model, HttpSession session)
    {
        String a = data.getAgencyId();
        poductService.deletePro(data);
        return redirectList(model, session,"/agency/addProduct.do?id="+a);
    }

    @RequestMapping(value = "/delete.do", method = RequestMethod.POST)
    @ResponseBody
    public void delete(ProductAgent data, ModelMap model, HttpSession session)
    {

        poductService.deletePro(data);
    }

    @RequestMapping(value = "/oldPriceDetails.do", method = RequestMethod.GET)
    public String oldPriceDetails(ProductAgent data, ModelMap model, HttpSession session)
    {
        List<AgencyProductOldPrice> oldPriceDetails= poductService.getOldPriceDetails(data);
        model.addAttribute("data",oldPriceDetails);
        return "agency/agency_oldPrice_details";
    }
}
