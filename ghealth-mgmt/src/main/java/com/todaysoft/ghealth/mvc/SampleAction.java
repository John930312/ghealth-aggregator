package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.ISampleService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("/sample")
public class SampleAction extends BaseAction
{
    @Autowired
    private ISampleService service;
    
    @Autowired
    private IOrderService orderService;
    
    @RequestMapping("/list.jsp")
    public String list(OrderSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Order> pagination = service.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "sample/sample_list";
    }
    
    @RequestMapping(value = "modifyStatus.jsp", method = RequestMethod.POST)
    public String modifyStatus(Order data, ModelMap model, HttpSession session)
    {
        service.modifyStatus(data);
        service.sampleSignedSend(data);
        return redirectList(model, session, "/sample/list.jsp");
    }
    
    @RequestMapping(value = "/getInformation.do", method = RequestMethod.GET)
    public String getInformation(String orderCode, ModelMap model, HttpSession session)
    {
        Object obj = session.getAttribute("sess");
        List<Order> list = new ArrayList<>();
        if (!Objects.isNull(obj))
        {
            list.addAll((List<Order>)obj);
        }
        
        OrderSearcher orderSearcher = new OrderSearcher();
        orderSearcher.setOrderCode(orderCode);
        orderSearcher.setStatus("1");
        List<Order> orders = orderService.list(orderSearcher);
        
        List<String> codes = new ArrayList<>();
        
        for (Order a : list)
        {
            codes.add(a.getCode());
        }
        List<Order> orders1 = new ArrayList<>();
        
        for (int i = 0; i < orders.size(); i++)
        {
            if (!codes.contains(orders.get(i).getCode()))
            {
                orders1.add(orders.get(i));
            }
        }
        
        list.addAll(orders1);
        
        model.addAttribute("list", list);
        
        if (!CollectionUtils.isEmpty(orders))
        {
            model.addAttribute("message", "true");
            session.setAttribute("sess", list);
        }
        else
        {
            model.addAttribute("message", "false");
        }
        
        return "sample/sample_iframe";
    }
    
    @RequestMapping(value = "sweepcode.jsp", method = RequestMethod.POST)
    public String sweepcode(Order data, ModelMap model, HttpSession session)
    {
        service.modifyStatus(data);
        
        service.sampleSignedSend(data);
        
        session.removeAttribute("sess");
        
        return redirectList(model, session, "/sample/list.jsp");
        
    }
    
    @RequestMapping(value = "/remove.do", method = RequestMethod.POST)
    @ResponseBody
    public void remove(HttpSession session)
    {
        session.removeAttribute("sess");
    }
    
}
