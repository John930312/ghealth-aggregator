package com.todaysoft.ghealth.mvc;

import com.todaysoft.ghealth.model.Order;
import com.todaysoft.ghealth.model.OrderSearcher;
import com.todaysoft.ghealth.service.IOrderService;
import com.todaysoft.ghealth.service.ISampleSendService;
import com.todaysoft.ghealth.support.BaseAction;
import com.todaysoft.ghealth.support.PagerArgs;
import com.todaysoft.ghealth.support.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/sampleSend")
public class SampleSendAction extends BaseAction
{
    @Autowired
    private ISampleSendService service;
    
    @Autowired
    private IOrderService orderService;
    
    @RequestMapping("/list.jsp")
    public String list(OrderSearcher searcher, PagerArgs pagerArgs, ModelMap model, HttpSession session)
    {
        Pagination<Order> pagination = service.search(searcher, pagerArgs.getPageNo(), pagerArgs.getPageSize());
        model.addAttribute("searcher", searcher);
        model.addAttribute("pagination", pagination);
        session.setAttribute("s-searcher", searcher);
        return "sampleSend/sampleSend_list";
    }
    
    @RequestMapping(value = "send.jsp", method = RequestMethod.POST)
    public String send(Order data, ModelMap model, HttpSession session)
    {
        service.send(data);
        service.sampleDeliveredSend(data);
        return redirectList(model, session, "/sampleSend/list.jsp");
    }
}
