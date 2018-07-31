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
import com.todaysoft.ghealth.model.customer.CustomerSearcher;
import com.todaysoft.ghealth.service.IAreaService;
import com.todaysoft.ghealth.service.ICustomerService;
import com.todaysoft.ghealth.support.BaseAction;
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
    
    private String url = "/customer/list.jsp";
    
    @RequestMapping("/list.jsp")
    public String list(CustomerSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Customer> pagination = service.searcher(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("pagination", pagination);
        model.addAttribute("searcher", searcher);
        session.setAttribute("s-searcher", searcher);
        return "customer/customer_list";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.GET)
    @FormInputView
    public String modify(String id, ModelMap model)
    {
        Customer data = service.get(id);
        model.addAttribute("data", data);
        Area provinceArea = new Area();
        if (!StringUtils.isEmpty(data.getProvince()))
        {
            provinceArea.setId(data.getProvince());
        }
        
        if (!StringUtils.isEmpty(data.getProvince()))
        {
            List<Area> province = areaService.findByParentId(data.getProvince());
            model.addAttribute("province", province);
        }
        Area cityArea = new Area();
        if (!StringUtils.isEmpty(data.getCity()))
        {
            cityArea.setId(data.getCity());
        }
        
        if (!StringUtils.isEmpty(data.getCity()))
        {
            List<Area> city = areaService.findByParentId(data.getCity());
            model.addAttribute("city", city);
        }
        List<Area> list = areaService.findProvince();
        model.addAttribute("list", list);
        return "customer/customer_modify";
    }
    
    @RequestMapping(value = "/modify.jsp", method = RequestMethod.POST)
    @FormSubmitHandler
    public String modify(Customer data, ModelMap model, HttpSession session)
    {
        service.modify(data);
        return redirectList(model, session, url);
    }
    
    @RequestMapping(value = "/delete.jsp", method = RequestMethod.POST)
    @ResponseBody
    public boolean delete(String id, ModelMap model, HttpSession session)
    {
        return service.delete(id);
    }
    
    @RequestMapping("/display.jsp")
    public String display(String id, ModelMap model)
    {
        Customer data = service.get(id);
        model.addAttribute("data", data);
        
        return "customer/customer_details";
    }
    
    @RequestMapping("/getAreas")
    @ResponseBody
    public List<Area> findByParentId(ModelMap model, String parentId)
    {
        List<Area> result = areaService.findByParentId(parentId);
        return result;
    }
    
    @RequestMapping(value = "/reload.do")
    public String reload(ModelMap model, HttpSession session)
    {
        return redirectList(model, session, url);
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
