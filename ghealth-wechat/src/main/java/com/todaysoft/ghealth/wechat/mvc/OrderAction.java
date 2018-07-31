package com.todaysoft.ghealth.wechat.mvc;

import com.todaysoft.ghealth.wechat.model.Customer;
import com.todaysoft.ghealth.wechat.model.CustomerData;
import com.todaysoft.ghealth.wechat.model.Order;
import com.todaysoft.ghealth.wechat.mvc.base.BaseAction;
import com.todaysoft.ghealth.wechat.service.IOrderService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
public class OrderAction extends BaseAction
{
    private Log log = LogFactory.getLog(OrderAction.class);
    
    @Autowired
    private IOrderService orderService;
    
    @RequestMapping("/list.jsp")
    public String list(ModelMap model, HttpSession session)
    {
        List<Order> datas = (List<Order>)session.getAttribute("orders");
        if (!CollectionUtils.isEmpty(datas))
        {
            Map<Boolean, List<Order>> map = datas.stream().collect(Collectors.partitioningBy(x -> Integer.valueOf(x.getStatus()) > 4));
            if (!CollectionUtils.isEmpty(map))
            {
                model.addAttribute("endOrders", map.get(true));
                model.addAttribute("doingOrders", map.get(false));
            }
        }
        return "business/order_list";
    }
    
    @RequestMapping("/detail.jsp")
    public String view(Order data, ModelMap model, HttpSession session)
    {
        Order order = orderService.getOrderById(data.getId());
        Optional.ofNullable(order.getReportContents()).ifPresent(x -> order.setExistContents(true));
        session.setAttribute(order.getCode(), order.getReportContents());
        session.setAttribute("token", order.getCode());
        model.addAttribute("data", order);
        return "business/order_detail";
    }
    
    @RequestMapping("/preview.jsp/{code}")
    public void preview(@PathVariable String code, HttpSession session, HttpServletResponse response)
    {
        String cacheCode = (String)session.getAttribute("token");
        if (StringUtils.isNotEmpty(cacheCode) && cacheCode.equals(cacheCode))
        {
            byte[] bytes = (byte[])session.getAttribute(code);
            try
            {
                orderService.preview(bytes, response);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
    
    @RequestMapping("/customerDetail.jsp")
    public String getCustomers(ModelMap model, HttpSession session)
    {
        List<Order> datas = (List<Order>)session.getAttribute("orders");
        Set<String> names = new HashSet<>();
        CustomerData data = new CustomerData();
        if (!CollectionUtils.isEmpty(datas))
        {
            List<Customer> customers = datas.stream().map(x -> x.getCustomer()).filter(v -> {
                boolean flag = !names.contains(v.getName());
                names.add(v.getName());
                return flag;
            }).collect(Collectors.toList());
            data.setCustomers(customers);
            data.setPhone(customers.get(0).getPhone());
        }
        model.addAttribute("data", data);
        return "business/person";
    }
    
}
