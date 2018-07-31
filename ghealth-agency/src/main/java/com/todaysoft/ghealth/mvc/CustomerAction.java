package com.todaysoft.ghealth.mvc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import com.todaysoft.ghealth.form.FormInputView;
import com.todaysoft.ghealth.form.FormSubmitHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.todaysoft.ghealth.base.response.model.Area;
import com.todaysoft.ghealth.model.customer.Customer;
import com.todaysoft.ghealth.model.customer.CustomerForm;
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import com.todaysoft.ghealth.service.IAreaService;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.ModelResolver;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;

@Controller
@RequestMapping("/customer")
public class CustomerAction extends BaseAction
{
    @Autowired
    private ICustomerService service;
    
    @Autowired
    private IAreaService areaService;
    
    @RequestMapping(value = "/list.jsp")
    public String list(CustomerSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Customer> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "customer/customer_list";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.GET)
    @FormInputView
    public String create(ModelMap model)
    {
        List<Area> list = areaService.findProvince();
        model.addAttribute("list", list);
        
        return "customer/customer_form";
    }
    
    @RequestMapping(value = "/create.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String create(CustomerForm data, ModelMap model, HttpSession session)
    {
        service.create(data);
        return redirectList(model, session);
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        Customer customer = service.get(id);
        Area provinceArea = new Area();
        if (!StringUtils.isEmpty(customer.getProvince()))
        {
            provinceArea.setId(customer.getProvince());
        }
        
        if (!StringUtils.isEmpty(customer.getProvince()))
        {
            List<Area> province = areaService.findByParentId(customer.getProvince());
            model.addAttribute("province", province);
        }
        Area cityArea = new Area();
        if (!StringUtils.isEmpty(customer.getCity()))
        {
            cityArea.setId(customer.getCity());
        }
        
        if (!StringUtils.isEmpty(customer.getCity()))
        {
            List<Area> city = areaService.findByParentId(customer.getCity());
            model.addAttribute("city", city);
        }
        
        model.addAttribute("data", customer);
        List<Area> list = areaService.findProvince();
        model.addAttribute("list", list);
        return "customer/customer_form";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(CustomerForm data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session);
    }
    
    @RequestMapping("/delete.jsp")
    @ResponseBody
    public boolean delete(String id, ModelMap model, HttpSession session)
    {
        return service.delete(id);
    }
    
    @RequestMapping("/display.jsp")
    public String display(String id, ModelMap model)
    {
        model.addAttribute("data", service.get(id));
        return "customer/customer_details";
    }
    
    private String redirectList(ModelMap model, HttpSession session)
    {
        model.clear();
        new ModelResolver(session.getAttribute("s-searcher"), model).resolve();
        return "redirect:/customer/list.jsp";
    }
    
    @RequestMapping("/getAreas")
    @ResponseBody
    public List<Area> findByParentId(ModelMap model, String parentId)
    {
        List<Area> result = areaService.findByParentId(parentId);
        return result;
    }


    @RequestMapping("/phone_unique.jsp")
    @ResponseBody
    public boolean isPhoneUnique(String id, String phone, ModelMap model)
    {
        return service.isPhoneUnique(id, phone);
    }

    @RequestMapping(value = "/reload.do")
    public String reload(ModelMap model, HttpSession session)
    {
        return redirectList(model, session);
    }

    @RequestMapping(value = "/getSelectList.do")
    @ResponseBody
    public Map<String, Object> getSelectList(CustomerSearcher searcher)
    {
        Map<String, Object> map = new HashMap<String, Object>();
        Pagination<Customer> datas = service.searcher(searcher, 1, 100);
        map.put("value",datas.getRecords());
        return map;
    }
}
